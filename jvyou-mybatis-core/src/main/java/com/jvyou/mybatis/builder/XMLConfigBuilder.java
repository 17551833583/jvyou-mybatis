package com.jvyou.mybatis.builder;

import cn.hutool.core.util.ClassUtil;
import com.jvyou.mybatis.annotations.*;
import com.jvyou.mybatis.cache.Cache;
import com.jvyou.mybatis.datasource.PooledDataSource;
import com.jvyou.mybatis.exception.XmlMapperException;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.mapping.SqlCommandType;
import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.xml.tag.MixedSqlNode;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/27 19:59
 * @Description XML配置构建器
 */
public class XMLConfigBuilder {

    public Configuration parse() {
        Configuration configuration = new Configuration();
        // 从注解中将Mapper里面的方法解析成MappedStatement
        this.parseAnnotationMapper(configuration);
        // 从 XML 文件将Mapper里面的方法解析成MappedStatement
        this.parseXmlMapper(configuration);
        // 解析数据源
        this.parseDataSource(configuration);
        return configuration;
    }

    /**
     * 解析数据源
     *
     * @param configuration 配置对象
     */
    private void parseDataSource(Configuration configuration) {
        // 获取数据源配置
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/jvyou-mybatis?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        String username = "root";
        String password = "123456";
        // 创建数据源
        configuration.setDataSource(new PooledDataSource(username, password, driver, url));
    }

    /**
     * 通过注解的方式将mapper里面的方法解析成MappedStatement
     *
     * @param configuration 配置对象
     */
    private void parseAnnotationMapper(Configuration configuration) {

        Set<Class<?>> classes = ClassUtil.scanPackage("com.jvyou.mybatis.mapper");
        for (Class<?> aClass : classes) {
            // 判断configuration是否默认开启二级缓存，如果不开启，还要判断 Mapper 是否有CacheNamespace.class注解
            boolean isCache = configuration.isCacheEnabled() ? true : aClass.isAnnotationPresent(CacheNamespace.class);
            Cache cache = isCache ? configuration.getCache(aClass.getName()) : null;

            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                String originalSql = "";
                SqlCommandType sqlCommandType = SqlCommandType.SELECT;
                if (method.isAnnotationPresent(Select.class)) {
                    originalSql = method.getAnnotation(Select.class).value();
                } else if (method.isAnnotationPresent(Update.class)) {
                    originalSql = method.getAnnotation(Update.class).value();
                    sqlCommandType = SqlCommandType.UPDATE;
                } else if (method.isAnnotationPresent(Insert.class)) {
                    originalSql = method.getAnnotation(Insert.class).value();
                    sqlCommandType = SqlCommandType.INSERT;
                } else if (method.isAnnotationPresent(Delete.class)) {
                    originalSql = method.getAnnotation(Delete.class).value();
                    sqlCommandType = SqlCommandType.DELETE;
                } else {
                    continue;
                }
                // 是否返回多行
                boolean isSelectMany = false;
                // 获取 Mapper 方法的返回值类型
                Class<?> returnType = null;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof Class) {
                    returnType = (Class<?>) genericReturnType;
                } else if (genericReturnType instanceof ParameterizedType) {
                    // ParameterizedType 表示带有泛型参数的类或者接口，比如 List<User>
                    isSelectMany = true;
                    returnType = ((ParameterizedType) genericReturnType).getActualTypeArguments().length > 0
                            ? (Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0]
                            : (Class<?>) ((ParameterizedType) genericReturnType).getRawType();
                }
                // 构建 MappedStatement
                MappedStatement mappedStatement = MappedStatement.builder()
                        .id(aClass.getName() + "." + method.getName())
                        .sql(originalSql)
                        .resultType(returnType)
                        .isSelectMany(isSelectMany)
                        .sqlCommandType(sqlCommandType)
                        .cache(cache)
                        .build();
                configuration.addMappedStatement(mappedStatement);
            }
        }

    }

    @SuppressWarnings("all")
    @SneakyThrows
    public void parseXmlMapper(Configuration configuration) {
        List<InputStream> inputStreams = XMLScanner.scanXMLFilesFromResource("com/jvyou/mapper");
        // 返回值类型处理器
        ReturnTypeHandler returnTypeHandler = new ReturnTypeHandler();
        for (InputStream inputStream : inputStreams) {
            SAXReader saxReader = new SAXReader();
            // 禁用 DTD 验证
            saxReader.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));
            Document document = saxReader.read(inputStream);
            // 获取根元素(mapper)
            Element rootElement = document.getRootElement();
            if (!"mapper".equals(rootElement.getName())) {
                throw new XmlMapperException("mybatis 映射文件的根节点必须是mapper");
            }
            // 获取命名空间（MappedStatement的id前缀）
            String namespace = rootElement.attributeValue("namespace");
            // 获取子元素(select、insert、update、delete)
            List<Element> elements = rootElement.elements();
            // 这边拿到的都是 select、insert、update、delete标签
            // 他们都是主SQL+if、where等标签
            for (Element element : elements) {
                String id = namespace + "." + element.attributeValue("id");
                if (configuration.getMappedStatement(id) != null) {
                    continue;
                }
                // 更新操作默认返回影响行数
                Class resultType = Integer.class;
                SqlCommandType sqlCommandType = SqlCommandType.SELECT;
                if (element.getName().equals("select")) {
                    resultType = returnTypeHandler.getReturnType(element.attributeValue("resultType"));
                } else if (element.getName().equals("update")) {
                    sqlCommandType = SqlCommandType.UPDATE;
                } else if (element.getName().equals("insert")) {
                    sqlCommandType = SqlCommandType.INSERT;
                } else if (element.getName().equals("delete")) {
                    sqlCommandType = SqlCommandType.DELETE;
                }
                MixedSqlNode mixedSqlNode = new SqlNodeParser().parseXml(element);

                // 构建 MappedStatement
                MappedStatement mappedStatement = MappedStatement.builder()
                        .id(id)
                        .sql("") // 获取 BoundSql 时会解析 SQL
                        .resultType(resultType)
                        .isSelectMany(false)
                        .sqlSource(mixedSqlNode)
                        .sqlCommandType(sqlCommandType)
                        .cache(null)
                        .build();
                configuration.addMappedStatement(mappedStatement);
            }
        }
    }


}

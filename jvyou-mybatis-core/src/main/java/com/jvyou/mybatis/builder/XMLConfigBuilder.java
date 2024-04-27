package com.jvyou.mybatis.builder;

import cn.hutool.core.util.ClassUtil;
import com.jvyou.mybatis.annotations.Select;
import com.jvyou.mybatis.mapping.MappedStatement;
import com.jvyou.mybatis.session.Configuration;
import com.jvyou.mybatis.utils.TypeUtils;

import java.lang.reflect.Method;
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
        // 解析 Mapper
        parseMapper(configuration);
        return configuration;
    }

    private void parseMapper(Configuration configuration) {

        Set<Class<?>> classes = ClassUtil.scanPackage("com.jvyou.mybatis.mapper");
        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Select.class)) {
                    Select select = method.getAnnotation(Select.class);
                    String originalSql = select.value();
                    // 获取 Mapper 方法的返回值类型
                    Class<?> returnType = TypeUtils.getMethodReturnType(method);
                    MappedStatement mappedStatement = MappedStatement.builder()
                            .id(aClass.getName() + "." + method.getName())
                            .sql(originalSql)
                            .resultType(returnType)
                            .build();
                    configuration.addMappedStatement(mappedStatement);
                }
            }
        }

    }

}

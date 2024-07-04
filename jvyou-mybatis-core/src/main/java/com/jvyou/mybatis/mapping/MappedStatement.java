package com.jvyou.mybatis.mapping;

import com.jvyou.mybatis.cache.Cache;
import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.parser.GenericTokenParser;
import com.jvyou.mybatis.parser.ParameterMappingTokenHandler;
import com.jvyou.mybatis.xml.DynamicContext;
import com.jvyou.mybatis.xml.tag.SqlNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/4/27 20:12
 * ---description 经过解析的 SQL 陈述语句
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MappedStatement implements SQLKeyword {

    /**
     * 唯一标识 eg: com.jvyou.mybatis.mapper.UserMapper.getAll
     * 由类名和方法名组成
     */
    private String id;
    /**
     * 原始的 SQL语句
     */
    private String sql;
    /**
     * 返回类型
     */
    private Class<?> resultType;
    /**
     * SQL 命令类型
     */
    private SqlCommandType sqlCommandType;
    /**
     * 是否是查询多条记录
     */
    private boolean isSelectMany;
    /**
     * 二级缓存,二级缓存是存放在 Configuration 对象的 caches Map集合当中的，键值为 Mapper 的全限定类名，值则是 Cache 对象，同一个 Mapper 的二级缓存是共享的
     */
    private Cache cache;

    /**
     * SQL 动态节点
     */
    private SqlNode sqlSource;

    /**
     * 获取 BoundSql
     *
     * @return 携带解析后的SQL和参数名称列表的 BoundSql 对象
     */
    public BoundSql getBoundSql(Object parameter) {
        // 动态节点不为空，需要解析动态SQL
        if (this.sqlSource != null) {
            DynamicContext context = new DynamicContext((Map<String, Object>) parameter);
            sqlSource.apply(context);
            this.sql = context.getSql()
                    .replace("\n", " ")  // 移除换行符
                    .replaceAll("\\s+", " "); // 移除多余的空格
        }

        // 解析 SQL
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser(SQL_OPEN_TOKEN, SQL_CLOSE_TOKEN, tokenHandler);
        String parsedSql = genericTokenParser.parse(sql);
        // 获取参数名称列表，这个是根据原始的 SQL 语句解析出来的
        List<String> params = tokenHandler.getParams();
        return new BoundSql(parsedSql, params);
    }

    public String getCacheKey(Object parameter) {
        return id + ":" + sql + ":" + parameter;
    }

}

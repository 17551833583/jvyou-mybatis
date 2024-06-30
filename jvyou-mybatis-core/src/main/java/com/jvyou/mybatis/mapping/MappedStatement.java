package com.jvyou.mybatis.mapping;

import com.jvyou.mybatis.cache.Cache;
import com.jvyou.mybatis.constant.SQLKeyword;
import com.jvyou.mybatis.parser.GenericTokenParser;
import com.jvyou.mybatis.parser.ParameterMappingTokenHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/27 20:12
 * @Description 经过解析的 SQL 陈述语句
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
     * 获取 BoundSql
     *
     * @return 携带解析后的SQL和参数名称列表的 BoundSql 对象
     */
    public BoundSql getBoundSql() {
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

package com.jvyou.mybatis.parser;


import com.jvyou.mybatis.exception.JvyouMybatisException;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 13:25
 * @Description 通用SQL令牌解析器
 * 将“”
 * ===>"select * from t_user where id=? and name=?"，同时存储 SQL 中占位符对应的参数名称
 */
public class GenericTokenParser {

    // 占位符开始标记 -- eg: #{
    private final String openToken;
    // 占位符结束标记 -- eg: }
    private final String closeToken;
    // 参数处理器
    private final TokenHandler tokenHandler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler tokenHandler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.tokenHandler = tokenHandler;
    }

    public String parse(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        int offset = 0;
        while (true) {
            int start = text.indexOf(openToken, offset);
            if (start == -1) {
                break;
            }
            result.append(text, offset, start);
            int end = text.indexOf(closeToken, start);
            if (end == -1) {
                throw new JvyouMybatisException("在 " + text.substring(start) + " 附近没有找到对应的结束标记");
            }
            String paramName = text.substring(start + openToken.length(), end);
            result.append(tokenHandler.handleToken(paramName));
            offset = end + closeToken.length();
        }
        // 循环结束需要将剩余部分添加到结果里面
        if (offset < text.length()) {
            result.append(text, offset, text.length());
        }

        return result.toString();
    }


}

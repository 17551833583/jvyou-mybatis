package com.jvyou.mybatis.parser;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/26 14:07
 * @Description 标记处理器
 */
public interface TokenHandler {

    /**
     * 处理标记
     *
     * @param content 参数内容
     * @return 标记解析后的内容 eg: 参数名称 -> ?
     */
    String handleToken(String content);

}

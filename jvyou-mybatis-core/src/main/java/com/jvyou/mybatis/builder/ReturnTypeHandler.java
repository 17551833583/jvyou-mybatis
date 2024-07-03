package com.jvyou.mybatis.builder;

import com.jvyou.mybatis.exception.XmlMapperException;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/2 18:34
 * @Description 返回类型处理器
 */
public class ReturnTypeHandler {

    private final Map<String, Class<?>> returnTypeMap = new HashMap<>();


    public ReturnTypeHandler() {
        returnTypeMap.put("int", int.class);
        returnTypeMap.put("Integer", Integer.class);
        returnTypeMap.put("long", long.class);
        returnTypeMap.put("Long", Long.class);
        returnTypeMap.put("float", float.class);
        returnTypeMap.put("Float", Float.class);
        returnTypeMap.put("double", double.class);
        returnTypeMap.put("Double", Double.class);
        returnTypeMap.put("boolean", boolean.class);
        returnTypeMap.put("Boolean", Boolean.class);
        returnTypeMap.put("String", String.class);
        returnTypeMap.put("Object", Object.class);
        returnTypeMap.put("void", void.class);
        returnTypeMap.put("Void", Void.class);
        returnTypeMap.put("byte", byte.class);
        returnTypeMap.put("Byte", Byte.class);
        returnTypeMap.put("short", short.class);
        returnTypeMap.put("Short", Short.class);
        returnTypeMap.put("char", char.class);
        returnTypeMap.put("Character", Character.class);
        returnTypeMap.put("Char", Character.class);
        returnTypeMap.put("char[]", char[].class);
        returnTypeMap.put("Char[]", Character[].class);
        returnTypeMap.put("byte[]", byte[].class);
        returnTypeMap.put("Byte[]", Byte[].class);
        returnTypeMap.put("short[]", short[].class);
        returnTypeMap.put("Short[]", Short[].class);
        returnTypeMap.put("int[]", int[].class);
        returnTypeMap.put("Integer[]", Integer[].class);
        returnTypeMap.put("long[]", long[].class);
        returnTypeMap.put("Long[]", Long[].class);
        returnTypeMap.put("float[]", float[].class);
        returnTypeMap.put("Float[]", Float[].class);
        returnTypeMap.put("double[]", double[].class);
        returnTypeMap.put("Double[]", Double[].class);
        returnTypeMap.put("boolean[]", boolean[].class);
        returnTypeMap.put("Boolean[]", Boolean[].class);
        returnTypeMap.put("String[]", String[].class);
        returnTypeMap.put("Object[]", Object[].class);
    }

    @SneakyThrows
    public Class<?> getReturnType(String returnTypeString) {
        if (returnTypeString == null || returnTypeString.trim().length() == 0) {
            throw new XmlMapperException("returnType is null");
        }
        Class<?> returnType = returnTypeMap.get(returnTypeString);
        if (returnType == null) {
            returnType = Class.forName(returnTypeString);
        }
        return Optional.ofNullable(returnType).orElseThrow(() -> new XmlMapperException("returnType is null"));
    }
}

package com.jvyou.mybatis.builder;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @since 2024/7/2 16:46
 * ---description XML文件扫描器
 */
public class XMLScanner {

    /**
     * 从当前线程的类加载器中获取指定目录下的所有 XML 文件，并返回一个输入流列表
     *
     * @param directory 资源目录下指定的目录
     * @return 输入流列表
     */
    @SneakyThrows
    public static List<InputStream> scanXMLFilesFromResource(String directory) {
        List<InputStream> xmlInputStreams = new ArrayList<>();
        // 获取当前线程的类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 通过类加载器获取资源的URL
        URL resourceUrl = classLoader.getResource(directory);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + directory);
        }
        // 使用 NIO API 遍历目录下的所有文件
        Files.walkFileTree(Paths.get(resourceUrl.toURI()), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".xml")) {
                    xmlInputStreams.add(Files.newInputStream(file));
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return xmlInputStreams;
    }

}

package com.jvyou.mybatis.xml;

import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/6/30 21:26
 * @Description XML解析测试
 */

public class XmlParseTest {

    @SneakyThrows
    @Test
    public void testParseXml() {
        long currentTimeMillis = System.currentTimeMillis();
        SAXReader saxReader = new SAXReader();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/jvyou/mapper/UserMapper.xml");
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        elements.forEach(element -> {
            System.out.println(element.getName());
            System.out.println(element.getText());
            System.out.println("==============");
        });
        System.out.println("耗时：" + (System.currentTimeMillis() - currentTimeMillis));
    }

    @SneakyThrows
    @Test
    public void testParseXml2() {
        long currentTimeMillis = System.currentTimeMillis();
        SAXReader saxReader = new SAXReader();
        // 禁用 DTD 验证
        saxReader.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/jvyou/mapper/UserMapper.xml");
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        // 这边拿到的都是 select、insert、update、delete标签
        // 他们都是主SQL+if、where等标签
        for (Element element : elements) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            System.out.println("id:" + id);
            System.out.println("resultType:" + resultType);
            // 主SQL语句和下面的子标签都是 Node
            List<Node> childrenNodes = element.content();
            for (Node childrenNode : childrenNodes) {
                parseXml(childrenNode);
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - currentTimeMillis));
    }


    public void parseXml(Node node) {
        if (node.getNodeType() == Node.TEXT_NODE) {
            if (node.getText().trim().length() != 0) {
                System.out.println("SQL:" + node.getText().trim());
            }
        } else if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String test = element.attributeValue("test");
            if (test != null) {
                System.out.println("test:" + test);
            }
            List<Node> nodes = element.content();
            nodes.forEach(this::parseXml);
        }
    }

}

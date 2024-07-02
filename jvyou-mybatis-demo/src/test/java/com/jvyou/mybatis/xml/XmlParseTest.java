package com.jvyou.mybatis.xml;

import com.jvyou.mybatis.xml.tag.*;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
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
            MixedSqlNode mixedSqlNode = parseXml(element);

            DynamicContext dynamicContext = new DynamicContext();
            dynamicContext.bind("id", 1);
            dynamicContext.bind("name", "jvyou");
            dynamicContext.bind("age", 18);
            mixedSqlNode.apply(dynamicContext);
            System.out.println(dynamicContext.getSql());
            System.out.println("==============");
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - currentTimeMillis));
    }


    public MixedSqlNode parseXml(Element element) {
        // 主SQL语句和下面的子标签都是 Node
        List<Node> childrenNodes = element.content();
        List<SqlNode> sqlNodes = new ArrayList<>();

        for (Node childNode : childrenNodes) {
            SqlNode sqlNode = null;
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                String nodeName = childElement.getName();
                if (nodeName.equals("if")) {
                    String test = childElement.attributeValue("test");
                    if (test == null) {
                        throw new RuntimeException("if标签的test属性不能为空");
                    }
                    sqlNode = new IfSqlNode(test, parseXml(childElement));
                } else if (nodeName.equals("where")) {
                    sqlNode = new WhereSqlNode(parseXml(childElement));
                }

            } else {
                String sql = childNode.getText().trim();
                if (sql.length() != 0) {
                    sqlNode = new TextSqlNode(sql);
                }
            }
            if (sqlNode != null) {
                sqlNodes.add(sqlNode);
            }
        }

        return new MixedSqlNode(sqlNodes);
    }

}

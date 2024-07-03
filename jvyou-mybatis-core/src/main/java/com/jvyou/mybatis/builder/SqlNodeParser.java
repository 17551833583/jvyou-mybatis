package com.jvyou.mybatis.builder;

import com.jvyou.mybatis.xml.tag.*;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/7/2 16:52
 * @Description SqlNode解析器，使用dom4j解析xml文件，将 select、update、insert、delete等标签下面的子节点解析成SqlNode
 */
public class SqlNodeParser {

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
                    sqlNode = new StaticTextSqlNode(sql);
                }
            }
            if (sqlNode != null) {
                sqlNodes.add(sqlNode);
            }
        }
        return new MixedSqlNode(sqlNodes);
    }

}

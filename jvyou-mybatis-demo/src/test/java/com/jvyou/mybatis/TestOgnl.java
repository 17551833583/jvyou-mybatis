package com.jvyou.mybatis;

import com.jvyou.mybatis.entity.User;
import ognl.Ognl;
import ognl.OgnlException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/5/22 15:53
 * @Description
 */
public class TestOgnl {

    @Test
    void testOgnl3() throws OgnlException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "jvyou");
        map.put("age", 18);
        map.put("user", new User(1, "jvyou", 18));
        Object name = Ognl.getValue("user.age>=18", map);
        System.out.println(name);
    }

    @Test
    void testOgnl2() throws OgnlException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "jvyou");
        map.put("age", 18);
        Object name = Ognl.getValue("name", map);
        System.out.println(name);
    }

    @Test
    void testOgnl() throws OgnlException {
        User user = new User(1, "jvyou", 18);
        Object name = Ognl.getValue("name", user);
        System.out.println(name);
    }

}

package com.jvyou.mybatis.proxy.staticPro;

/**
 * @author 橘柚
 * @version 1.0-SNAPSHOT
 * @Date 2024/4/25 21:49
 * @Description 婚庆公司
 */
public class WeddingCompany implements Marry {

    // 代理的对象
    private final Marry target;

    public WeddingCompany(Marry target) {
        this.target = target;
    }

    @Override
    public void marry() {
        System.out.println("代理结婚开始");
        target.marry();
        System.out.println("代理结婚结束");
    }
}

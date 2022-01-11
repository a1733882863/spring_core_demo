package com.xz;

import com.spring.XieZhiApplicationContext;
import com.xz.service.Orderservice;

import java.lang.reflect.InvocationTargetException;

public class ApplicationTest {
    public static void main(String[] args) throws Exception {
        XieZhiApplicationContext xieZhiApplicationContext = new XieZhiApplicationContext(AppConfig.class);
        Orderservice orderService = (Orderservice)xieZhiApplicationContext.getBean("orderService");
        orderService.methodA();
    }
}

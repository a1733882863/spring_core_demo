package com.xz;

import com.spring.XieZhiApplicationContext;
import com.xz.service.OrderService;
import com.xz.service.OrderserviceImpl;

public class ApplicationTest {
    public static void main(String[] args) throws Exception {
        XieZhiApplicationContext xieZhiApplicationContext = new XieZhiApplicationContext(AppConfig.class);
        OrderService orderService = (OrderService) xieZhiApplicationContext.getBean("orderService");
        orderService.methodA();
    }
}

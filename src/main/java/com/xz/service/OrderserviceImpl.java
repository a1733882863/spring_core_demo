package com.xz.service;


import com.spring.Component;

@Component("orderService")
public class OrderserviceImpl implements OrderService {

    public void methodA() {
        System.out.println("这是orderService的methodA");
    }
}

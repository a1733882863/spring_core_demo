package com.xz.service;


import com.spring.Component;
import com.spring.Scope;

@Component("orderService")
public class Orderservice implements AllService{

    public void methodA() {
        System.out.println("这是orderService的methodA");
    }
}

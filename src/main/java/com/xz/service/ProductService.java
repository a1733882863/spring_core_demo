package com.xz.service;

import com.spring.Component;
import com.spring.Scope;

@Component("productService")
@Scope("prototype")
public class ProductService {

    public void methodA() {
        System.out.println("这是productService的methodA");
    }
}

package com.xz.service;

public class BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean,String beanName) {
        System.out.println("bean前置操作");
        return bean;
    }
    Object postProcessAfterInitialization(Object bean,String beanName) {
        System.out.println("bean的后置操作");
        return bean;
    }
}

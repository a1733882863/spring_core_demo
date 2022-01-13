package com.xz.service;

import com.spring.BeanPostProcessor;
import com.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component("xieZhiBeanPostProcessor")
public class XieZhiBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("bean初始化前");
        return bean;
    }
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("bean初始化后");
        if (beanName.equals("orderService")) {
            Object instance = Proxy.newProxyInstance(XieZhiBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("代理逻辑");
                    return method.invoke(bean, args);
                }
            });
            return instance;
        }
        return bean;
    }
}

package com.spring;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XieZhiApplicationContext {

    ConcurrentHashMap<String,BeanDifinition> beanDifinitionMap = new ConcurrentHashMap();
    ConcurrentHashMap<String,Object> singleObjectMap = new ConcurrentHashMap();
    Class<?> configclazz;

    public XieZhiApplicationContext(Class<?> config) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.configclazz = config;
        //扫描配置类生成BeanDefinition
        scan(configclazz);
        //接下来对beanDifinitions操作
        for (Map.Entry<String,BeanDifinition> entry : beanDifinitionMap.entrySet()) {
            String beanName = entry.getKey();
            if (entry.getValue().getScope().equals("single")) {
                singleObjectMap.put(beanName, createBean(beanName,entry.getValue()));
            }
        }

    }

    public Object getBean(String beanName) throws Exception {
        Object bean = null;
        if (beanDifinitionMap.containsKey(beanName)) {
            BeanDifinition beanDifinition = beanDifinitionMap.get(beanName);
            if (beanDifinition.getScope().equals("single")) {
                bean = singleObjectMap.get(beanName);
            }else if(beanDifinition.getScope().equals("prototype")) {
                bean = createBean(beanName,beanDifinition);
            }
        }else {
            throw new Exception("没有此bean");
        }
        return bean;
    }

    private Object createBean(String beanName, BeanDifinition beanDifinition) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = beanDifinition.getClazz();
        Object instance = clazz.getConstructor().newInstance();
        return instance;
    }


    private void scan(Class<?> configclazz) {
        ComponentScan componentScan = configclazz.getDeclaredAnnotation(ComponentScan.class);
        String scanPath = componentScan.value();
        //用类加载器加载
        scanPath = scanPath.replace(".","/");
        URL resource = configclazz.getClassLoader().getResource(scanPath);
        File fileFirst = new File(resource.getFile());
        File[] javaFiles = fileFirst.listFiles();
        String javaPath4Create;
        for (File javaFile : javaFiles) {
            javaPath4Create = javaFile.getPath();
            javaPath4Create = javaPath4Create.substring(javaPath4Create.indexOf("com"), javaPath4Create.lastIndexOf(".class"));
            javaPath4Create = javaPath4Create.replace("\\",".");
            Class<?> clazz;
            String beanName;
            try {
                clazz = configclazz.getClassLoader().loadClass(javaPath4Create);
                //前面已经获取到了java 的class 对象, 现在要开始对每个java class对象进行注解的解析
                Component component = clazz.getDeclaredAnnotation(Component.class);
                beanName = component.value();
                BeanDifinition beanDifinition = new BeanDifinition();
                Scope scope = clazz.getDeclaredAnnotation(Scope.class);
                if (scope != null && "prototype".equals(scope.value())) {
                    beanDifinition.setScope("prototype");
                }
                beanDifinition.setScope("single");
                beanDifinition.setClazz(clazz);
                beanDifinitionMap.put(beanName,beanDifinition);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

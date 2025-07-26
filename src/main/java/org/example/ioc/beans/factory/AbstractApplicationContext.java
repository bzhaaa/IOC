package org.example.ioc.beans.factory;

import org.example.ioc.beans.support.BeanDefinitionReader;
import org.example.ioc.beans.support.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractApplicationContext implements ApplicationContext{

    protected BeanDefinitionReader beanDefinitionReader;

    protected Map<String, Object> singletonObjects = new HashMap<>();

    protected String configLocation;

    @Override
    public void refresh() throws Exception {
        beanDefinitionReader.loadBeanDefinitions(configLocation);
        finishBeanInitialization();
    }

    private void finishBeanInitialization() throws Exception {
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            getBean(beanDefinitionName);
        }
    }
}

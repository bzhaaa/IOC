package org.example.ioc.beans.support;

public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    void loadBeanDefinitions(String location) throws Exception;
}

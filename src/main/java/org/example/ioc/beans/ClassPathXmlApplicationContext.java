package org.example.ioc.beans;

import org.example.ioc.beans.factory.AbstractApplicationContext;
import org.example.ioc.beans.support.BeanDefinitionRegistry;
import org.example.ioc.beans.utils.StringUtils;
import org.example.ioc.beans.xml.XmlBeanDefinitionReader;

import java.lang.reflect.Method;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        this.configLocation = configLocation;
        beanDefinitionReader = new XmlBeanDefinitionReader();
        try {
            this.refresh();
        } catch (Exception e) {

        }
    }

    @Override
    public Object getBean(String name) throws Exception{
        Object o = singletonObjects.get(name);
        if (null != o) {
            return o;
        }
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();
        BeanDefinition beanDefinition = registry.getBeanDefinition(name);
        String className = beanDefinition.getClassName();
        Class<?> clazz = Class.forName(className);
        Object newObj = clazz.newInstance();
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            String propertyName = propertyValue.getName();
            String ref = propertyValue.getRef();
            String value = propertyValue.getValue();
            if (ref != null && !ref.equals("")) {
                Object refBean = getBean(ref);
                String methodByFieldName = StringUtils.getSetterMethodByFieldName(propertyName);
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.getName().equals(methodByFieldName)) {
                        method.invoke(newObj, refBean);
                    }
                }
            }
            if (value != null && !value.equals("")) {
                String methodByFieldName = StringUtils.getSetterMethodByFieldName(propertyName);
                Method method = clazz.getMethod(methodByFieldName, String.class);
                method.invoke(newObj, value);
            }
        }
        singletonObjects.put(name, newObj);
        return newObj;
    }


    @Override
    public <T> T getBean(String name, Class<? extends T> requiredType) throws Exception{
        Object bean = getBean(name);
        if (bean == null) {
            return null;
        }
        return requiredType.cast(bean);
    }


}

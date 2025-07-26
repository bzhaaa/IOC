package org.example.ioc.beans.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.ioc.beans.BeanDefinition;
import org.example.ioc.beans.MutablePropertyValues;
import org.example.ioc.beans.PropertyValue;
import org.example.ioc.beans.support.BeanDefinitionReader;
import org.example.ioc.beans.support.BeanDefinitionRegistry;
import org.example.ioc.beans.support.SimpleBeanDefinitionRegistry;

import java.io.InputStream;
import java.util.List;

public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader() {
        this.registry = new SimpleBeanDefinitionRegistry();
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        SAXReader saxReader = new SAXReader();
        InputStream is = XmlBeanDefinitionReader.class.getClassLoader().getResourceAsStream(location);
        Document document = saxReader.read(is);
        Element rootElement = document.getRootElement();
        List<Element> bean = rootElement.elements("bean");
        for (Element element : bean) {
            String id = element.attributeValue("id");
            String className = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(id);
            beanDefinition.setClassName(className);

            MutablePropertyValues propertyValues = new MutablePropertyValues();

            List<Element> property = element.elements("property");
            for (Element o : property) {
                String name = o.attributeValue("name");
                String value = o.attributeValue("value");
                String ref = o.attributeValue("ref");
                PropertyValue propertyValue = new PropertyValue(name, ref, value);
                propertyValues.addPropertyValue(propertyValue);
            }

            beanDefinition.setPropertyValues(propertyValues);

            registry.registerBeanDefinition(id, beanDefinition);
        }
    }
}

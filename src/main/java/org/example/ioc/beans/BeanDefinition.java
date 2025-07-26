package org.example.ioc.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeanDefinition {

    private String id;

    private String className;

    private MutablePropertyValues propertyValues;

    public BeanDefinition() {
        this.propertyValues = new MutablePropertyValues();
    }
}

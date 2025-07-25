package org.example.ioc.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MutablePropertyValue implements Iterable<PropertyValue>{

    private final List<PropertyValue> propertyValueList;

    public MutablePropertyValue() {
        this.propertyValueList = new ArrayList<>();
    }

    public MutablePropertyValue(List<PropertyValue> propertyValueList) {
        if (propertyValueList == null) {
            this.propertyValueList = new ArrayList<PropertyValue>();
        } else {
            this.propertyValueList = propertyValueList;
        }
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return propertyValueList.isEmpty();
    }

    public MutablePropertyValue addPropertyValue(PropertyValue propertyValue) {
        for (int i = 0; i < propertyValueList.size(); i++) {
            PropertyValue propertyValue1 = propertyValueList.get(i);
            if (propertyValue1.getName().equals(propertyValue.getName())) {
                propertyValueList.set(i, propertyValue);
            }
            return this;
        }
        propertyValueList.add(propertyValue);
        return this;
    }


    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    @Override
    public Iterator<PropertyValue> iterator() {
        return propertyValueList.iterator();
    }
}

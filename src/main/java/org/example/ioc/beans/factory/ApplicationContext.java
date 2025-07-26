package org.example.ioc.beans.factory;

public interface ApplicationContext extends BeanFactory{

    void refresh() throws Exception;
}

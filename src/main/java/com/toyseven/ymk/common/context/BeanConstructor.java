package com.toyseven.ymk.common.context;

import org.springframework.context.ApplicationContext;

public class BeanConstructor {
	
	private final String beanName;
	
	public BeanConstructor(String beanName) {
		this.beanName = beanName;
	}
	
    public Object getBean() {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(beanName);
    }
    
}
 
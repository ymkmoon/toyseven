package com.toyseven.ymk.common.config;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class H2Runner implements ApplicationRunner {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private DataSource dataSource;
 
    public H2Runner(DataSource dataSource) {
        this.dataSource = dataSource;
    }
 
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
        	logger.info("url: "+connection.getMetaData().getURL());
        	logger.info("UserName: "+connection.getMetaData().getUserName());
        }
    }
}

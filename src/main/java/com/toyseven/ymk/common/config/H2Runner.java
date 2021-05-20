package com.toyseven.ymk.common.config;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class H2Runner implements ApplicationRunner {
    private DataSource dataSource;
 
    public H2Runner(DataSource dataSource) {
        this.dataSource = dataSource;
    }
 
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            log.info("url: "+connection.getMetaData().getURL());
            log.info("UserName: "+connection.getMetaData().getUserName());
        }
    }
}

package com.toyseven.ymk.common.config;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.toyseven.ymk.common.model.dto.CustomLocalDateTimeDeserializer;
import com.toyseven.ymk.common.model.dto.CustomLocalDateTimeSerializer;

@Configuration
public class ObjectMapperConfig {
	@Bean
    public ObjectMapper objectMapper() {
		SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        simpleModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}

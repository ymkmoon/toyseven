package com.toyseven.ymk.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Configuration
public class SqsConfig {
	
	public static final String ACCESS_KEY = "ACCESS_KEY";
	public static final String ACCESS_SECRET = "ACCESS_SECRET";
	public static final String REGION = "REGION";

    @Bean
    public SQSConnectionFactory sqsConnectionFactory() {
    	// Create the connection factory based on the config
    	BasicAWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, ACCESS_SECRET);
    	AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion(REGION).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
        return new SQSConnectionFactory(new ProviderConfiguration(), sqs);
    }
}

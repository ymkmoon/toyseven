package com.toyseven.ymk.common;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.toyseven.ymk.common.dto.VocAnswerDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SqsComponent {
	
	private final SQSConnectionFactory sqsConnectionFactory;
	
	private void makeQueue(SQSConnection connection, String queueName) throws JMSException{
        // Get the wrapped client
        AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();

        // Create an SQS queue named MyQueue, if it doesn't already exist
        if (!client.queueExists(queueName)) {
            client.createQueue(queueName);
        }
    }
	
	private SQSConnection createSqsConnection(String queueName) throws JMSException {
		SQSConnection connection = sqsConnectionFactory.createConnection();
		makeQueue(connection, queueName);
		// makeFifoQueue(connection, queueName);
		return connection;
	}
	
	private Session createJmsSession(SQSConnection connection) throws JMSException {
		// Create the JMS session
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public String sendMessage(String queueName, VocAnswerDto.Request vocAnswerRequest) throws JMSException {
		
		String message = vocAnswerRequest.getContent();

		SQSConnection connection = createSqsConnection(queueName);
		Session session = createJmsSession(connection);
		Queue queue = session.createQueue(queueName);

		MessageProducer producer = session.createProducer(queue);
		TextMessage textMessage = session.createTextMessage(message);
		producer.send(textMessage);
		
		producer.close();
		session.close();
		connection.close();
		
        return message;
	}
	
	public String receive(final String queueName) throws JMSException {
		SQSConnection connection = createSqsConnection(queueName);
		Session session = createJmsSession(connection);
		Queue queue = session.createQueue(queueName);

		// Create a consumer for the 'queueName'
		MessageConsumer consumer = session.createConsumer(queue);
		// Start receiving incoming messages
		connection.start();

		// Receive a message from 'MyQueue' and wait up to 1 second
		Message receivedMessage = consumer.receive(1000);
		
		consumer.close();
		session.close();
		connection.close();
		
		// Cast the received message as TextMessage and display the text
		if (receivedMessage != null) {
			System.out.println("Received: " + ((TextMessage) receivedMessage).getText());
			return ((TextMessage) receivedMessage).getText();
		}
		return null;
    }
	
	private void makeFifoQueue(SQSConnection connection, String queueName) throws JMSException {
        // Get the wrapped client
        AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();

        String fQueueName = String.format("%s.fifo", queueName);
        // Create an Amazon SQS FIFO queue named MyQueue.fifo, if it doesn't already exist
        if (!client.queueExists(fQueueName)) {
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("FifoQueue", "true");
            attributes.put("ContentBasedDeduplication", "true");
            client.createQueue(new CreateQueueRequest().withQueueName(fQueueName).withAttributes(attributes));
        }
    }
}

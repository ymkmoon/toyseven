package com.toyseven.ymk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class ToysevenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToysevenApplication.class, args);
//		SpringApplication application = new SpringApplication(ToysevenApplication.class);
//		application.addListeners(new ApplicationPidFileWriter());
//		application.run(args);
	}

}

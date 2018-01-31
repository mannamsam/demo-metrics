package com.centrifuge.metrics.demometrics;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Spring Boot Application start up class
 * 
 * @author Ram Mannam
 * @version 1.0
 *
 */
@SpringBootApplication
public class DemoMetricsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMetricsApplication.class, args);
	}

	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
}

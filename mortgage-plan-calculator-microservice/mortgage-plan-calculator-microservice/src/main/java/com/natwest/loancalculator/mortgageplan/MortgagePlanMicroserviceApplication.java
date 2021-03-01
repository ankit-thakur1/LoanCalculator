package com.natwest.loancalculator.mortgageplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * This is the starting point for the Mortgage plan microservice
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class MortgagePlanMicroserviceApplication {

	/**
	 * This method starts up the microservice as a Spring application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MortgagePlanMicroserviceApplication.class, args);
	}

}

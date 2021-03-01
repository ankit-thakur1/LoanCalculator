package com.natwest.loancalculator.loanterms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * This is the starting point for the Loan terms calculation microservice
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class LoanTermsCalculatorMicroserviceApplication {

	/**
	 * This method starts up the microservice as a Spring application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(LoanTermsCalculatorMicroserviceApplication.class, args);
	}

}

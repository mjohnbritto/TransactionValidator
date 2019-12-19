package com.rabobank.TransactionValidator;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.Shell;

/**
 * 
 * @author johnbrittom
 *
 *         The spring boot application class
 */
@SpringBootApplication
public class TransactionValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionValidatorApplication.class, args);
	}

	@Bean
	public ApplicationRunner shellRunner(Shell shell) {
		return new NonInteractiveShellRunner(shell);
	}
}

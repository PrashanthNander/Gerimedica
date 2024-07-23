package com.gerimedica.assignment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Gerimedica REST API Documentation",
				description = "Gerimedica REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Prashanth",
						email = "Prashanth.nander0388@gmail.com"
				)
		)
)
public class GerimedicaAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerimedicaAssignmentApplication.class, args);
	}

}

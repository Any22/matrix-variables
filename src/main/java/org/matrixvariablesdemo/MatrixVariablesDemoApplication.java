package org.matrixvariablesdemo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MatrixVariablesDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatrixVariablesDemoApplication.class, args);
	}
	
	 @Bean
	    public ModelMapper modelMapper() {
	        return new ModelMapper();
	    }

}

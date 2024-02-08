package org.matrixvariablesdemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/********************************************************************************************************************************************
 * Java EE (up to version 8): If you are working with a Java EE version up to 8 or any framework that uses the Java EE validation API,
 *  you should use javax.validation.constraints.
 *  Jakarta EE (version 9 onwards): If you are using Jakarta EE version 9 or later, you should use jakarta.validation.constraints.
 *  The org.hibernate.validator.constraints package contains additional constraints provided by the Hibernate Validator framework, which is 
 *  an implementation of the Java Bean Validation API. These constraints are Hibernate-specific and may not be part of the standard Bean 
 *  Validation API.
 *  for example : 
 *  @CreditCardNumber: Validates that a given string is a valid credit card number.
	@Currency: Validates that a given string represents a valid currency.
	@Email: Validates that a given string is a valid email address.
	@Length: Validates that the length of the annotated element is between min and max (inclusive).
	@Range: Checks whether the annotated value lies between (inclusive) the specified minimum and maximum.
 *******************************************************************************************************************************************/



/*******************************************************************************************************************************************
 * Data transfer object that carries data from one layer to another for e.g the form data reaches the controller layer using DTO objects 
 * 
 ******************************************************************************************************************************************/
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
	
	@NotNull 
	private Integer customerId;
	
	@NotEmpty (message = "Customer name cannot be empty")
	private String customerName;
	
	@NotNull
	@Email (message = "check the email id format")
	private String email;
	
}

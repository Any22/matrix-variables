package org.matrixvariablesdemo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.matrixvariablesdemo.dto.CustomerDTO;
import org.matrixvariablesdemo.entity.Customer;
import org.matrixvariablesdemo.exceptions.CustomerNotFoundException;
import org.matrixvariablesdemo.exceptions.NoDataFoundException;
import org.matrixvariablesdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;


@Validated 
@RestController

@RequestMapping("/customers")
@CrossOrigin
public class CustomerController {
	
	private static final Log LOGGER=LogFactory.getLog(CustomerController.class) ;
	
	
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired 
	Environment environment;
	
	/***************************************************************************************************************
	 * Creating a new data object and returning an acknowledgment string message that customer DTO has been created 
	 * @param customerDTO
	 * @return A string message 
	 ***************************************************************************************************************/
	
	@PostMapping(value = "/create", consumes = {"application/json"})
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		 
		/******************************************************************************************************
		 * The @Valid annotation will automatically trigger validation for customerDTO
		 * No need to check for null here, as @Valid will handle validation .you don't need to manually throw 
		 * MethodArgumentNotValidException in your controller method
		 ******************************************************************************************************/
		 if ( null == customerDTO) {
			 	  return new ResponseEntity<String>("check data fields",HttpStatus.NO_CONTENT);
		  }
		 
        Customer createdCustomer = customerService.saveCustomer(customerDTO);   
        LOGGER.info(" Data for "+ createdCustomer.getCustomerName()+ " is saved !");
   
       
        return new ResponseEntity<>("Data created for : "+ customerDTO.getCustomerName(), HttpStatus.CREATED);
    }
	
	/*****************************************************************************************************************
	 * Fetching the list of customers from the database.
	 * @return A list of customerDTO
	 * 
	 *****************************************************************************************************************/
	 @GetMapping(produces= APPLICATION_JSON_VALUE, value = "/get")
	    public ResponseEntity<List<CustomerDTO>> getCustomer() throws NoDataFoundException {
		 
	       try { 
		 	List<CustomerDTO> customerDto = customerService.getAllCustomer();
	        
		        if (customerDto.isEmpty() || null == customerDto) {
		        	
		        	throw new NoDataFoundException("No customers are found in database");
		            
		        } 
	       	
		        return new ResponseEntity<>(customerDto, HttpStatus.OK);
	       } catch(CustomerNotFoundException CEx) {
	    	   
	    	   LOGGER.error(CEx.getStackTrace(),CEx);
	    	   throw CEx;
	    	  
			   
		   } catch (Exception ex) {
			   LOGGER.error(ex.getStackTrace(),ex);
	    	   throw ex;
		   }
	        
	    }
	 
	 @GetMapping( produces= APPLICATION_JSON_VALUE, value = "/get/{customerId}" )
	    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer customerId) throws CustomerNotFoundException {
		 
		    LOGGER.info("CUSTOMER id "+ customerId);
	        CustomerDTO customerDto = customerService.getCustomerById(customerId);
	      
	        
	        if (null == customerDto) {
	        	
	        	throw new CustomerNotFoundException(environment.getProperty("customer does not exist"));
	            
	        } 
	        	return new ResponseEntity<>(customerDto, HttpStatus.OK);
	        
	    }
	 
	@RequestMapping (value = "/get-emails/{email}", method = RequestMethod.GET)

	public ResponseEntity <CustomerDTO> getCustomerByEmailId (@MatrixVariable (pathVar = "email", required=true) String email){
		
		
		try {
			
			CustomerDTO customerWithEmail = customerService.getCustomerByEmail(email);
			return new ResponseEntity<CustomerDTO> (customerWithEmail, HttpStatus.OK);
			
			
		} catch (Exception ex) {
			   LOGGER.error(ex.getStackTrace(),ex);
	    	   throw ex;
		   }
	        
	}
		
    
	 
}




package org.matrixvariablesdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.matrixvariablesdemo.dto.CustomerDTO;
import org.matrixvariablesdemo.entity.Customer;
import org.matrixvariablesdemo.exceptions.CustomerNotFoundException;
import org.matrixvariablesdemo.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;




@Service
public class CustomerService {
	
	@Autowired
    private ModelMapper modelMapper;  
	
	@Autowired
	private CustomerRepository customerRepository;

	
	private static final Log LOGGER = LogFactory.getLog(CustomerService.class);
    
	/*************************************************************************************************************************
	 * 
	 * The method is getting list of Customer DTOs which was stored in the form of entities 
	 * 
	 * 
	 * @return list of CustomerDTO which was stored in the form of entities
	 * 
	 *************************************************************************************************************************/
	
	public List<CustomerDTO> getAllCustomer() {
		
		// getting all entities from DB 
	    List<Customer> customer = customerRepository.findAll();
	   
	    //creating a list of CustomerDTOs by converting entities into DTOs using "convertToDto" method
	    
	    @SuppressWarnings("unused")
		List<CustomerDTO> customerDTOs = new ArrayList<>();
	    
	    return customer.stream()
	            .map(this::convertToDto)
	            .collect(Collectors.toList());
	    
	}
	
    public CustomerDTO getCustomerById(Integer customerId) throws CustomerNotFoundException {
		
		// getting the customer from DB 
    	
	    Optional<Customer> customerOptional = customerRepository.findById(customerId);
	   
	    if (customerOptional.isPresent()) {
	    	//very important line otherwise we will get null
	    	Customer customer = customerOptional.get();
	    	LOGGER.info("customer is present "+customer.getCustomerName());
	    	 return modelMapper.map(customer, CustomerDTO.class);	
	      
	    }   else {
	    	LOGGER.info("the customerId doesnot exist"+ customerId);
	    	throw new CustomerNotFoundException("the customerId doesnot exist");
	    
	    }
	}
	/*************************************************************************************************************************
	 * creating a customer 
	 * Model Mapper is a java library which can map one object to another object (entity to DTO and DTO to entity)
	 * here CustomerEntity to CustomerDTO .
	 * https://www.baeldung.com/java-modelmapper  (For unit testing )
	 * 
	 * @param email
	 * 
	 * @param name
	 * 
	 * @return a message of successful creation
	 * 
	 **************************************************************************************************************************/
	
	 public Customer saveCustomer(CustomerDTO customerDTO) {
                   
	       Customer customerEntity = new Customer();
	    
	       customerEntity.setCustomerName(customerDTO.getCustomerName());
	       customerEntity.setEmail(customerDTO.getEmail());
	      
	       customerRepository.saveAndFlush(customerEntity);
	
	       return customerEntity;
	   
	    }

	 
	 
    /**************************************************************************************************************************************
     * 
     * @param customer
     * @return
     **************************************************************************************************************************************/
	 
    public CustomerDTO convertToDto(Customer customer) {
    	
    	// converting into DTO
        return modelMapper.map(customer, CustomerDTO.class);
    }

    
    /**************************************************************************************************************************************
     * 
     * @param customerId and customerDto
     * @return
     **************************************************************************************************************************************/
    
    
    public CustomerDTO updateCustomer(Integer customerId,CustomerDTO customerDto) throws CustomerNotFoundException {
    	Customer customer = null; 
    	try {
    	if (customerId == null) {
    	        throw new IllegalArgumentException("Customer ID cannot be null");
    	    }
    	
    	Optional<Customer> customerInRepo =  customerRepository.findById(customerId);
    	
    	if (customerInRepo.isPresent()) {
    		customer = customerInRepo.get();
    		customer.setEmail(customerDto.getEmail());
    		customer.setCustomerName(customerDto.getCustomerName());
    		customerRepository.saveAndFlush(customer);
    	
        } 	else {
        	
        	throw new CustomerNotFoundException("Customer does not exist");
           }
    	
    	} catch(Exception e) {
    		e.getStackTrace();
    		LOGGER.error(e.getStackTrace().toString());
    		throw e;
    	}
    	return  modelMapper.map(customer, CustomerDTO.class);
    
    }
    	
    
    public void deleteCustomer(Integer customerId) {
    	Customer customer = null;
    	Optional<Customer> customerInRepo =  customerRepository.findById(customerId);
    	
    	if (customerInRepo.isPresent()) {
    		customer = customerInRepo.get();
    		customerRepository.deleteById(customer.getCustomerId());
    
    	}
    		else {
        	
    			throw new CustomerNotFoundException("customer does not exist");
           }
        	
    }

	@SuppressWarnings("unchecked")
	public CustomerDTO getCustomerWithIds( Integer customerId) {
		Customer customer = null; 
	
			
			 Optional<Customer> customerInRepo =  customerRepository.findById(customerId);
			 if (customerInRepo.isPresent()) {
		    		customer = customerInRepo.get();
		    		LOGGER.info("customer is present "+customer.getCustomerName());
			    	 return  modelMapper.map(customer, CustomerDTO.class);
			 }
			    	 else {
			 	    	LOGGER.info("the customerId doesnot exist");
			 	    	throw new CustomerNotFoundException("the customerId doesnot exist");
			 	    
			 	    }
		  }

	
	public CustomerDTO getCustomerByEmail(String email) {
		
		 Optional<Customer> customerInRepo =  customerRepository.findCustomerByEmail(email);
		 if (customerInRepo.isPresent()) {
			 Customer customer = customerInRepo.get();
	    		LOGGER.info("customer is present "+customer.getCustomerName());
	    		return  modelMapper.map(customer, CustomerDTO.class);
		    
		 }
		    	 else {
		 	    	LOGGER.info("the customeremail doesnot exist");
		 	    	throw new CustomerNotFoundException("the customeremail doesnot exist");
		 	    
		 	    }
		
	}
		
		}
		
	


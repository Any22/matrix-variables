package org.matrixvariablesdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/***************************************************************************************************
 * An entity class is the code representation of data table 
 * Each instance corresponds each row of data table 
 * each instance variable represents to each column of table
 * persistence context
 * 
 ***************************************************************************************************/

@Entity
@Table(name="customers")
@NoArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customer_id")
	@SequenceGenerator(name = "seq_customer_id", sequenceName = "seq_customer_id", allocationSize = 1)
	private Integer customerId;
	
	@Column(name = "customer_name", nullable = false)
	private String customerName;
		
	@Column(name = "email_address", nullable = false)
	private String email;
	

	public Customer(Integer customerId, String customerName, String email) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.email = email;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	

}

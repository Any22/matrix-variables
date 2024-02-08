package org.matrixvariablesdemo.repository;

import java.util.Optional;

import org.matrixvariablesdemo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//DDL to create the table and DML to populate the data
// the interface extends JPAREpository and pass Entity class and type of primary key
// the query must contain entity name Customer not table name customers  similary the field name email not the 
// table column email_address
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>  {
	
	@Query("select c from Customer c where c.email = ?1")
	Optional<Customer> findCustomerByEmail (String email);
	
}



package com.yash.rest.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yash.rest.customerservice.dao.Customer;
import com.yash.rest.customerservice.exception.ResourceNotFoundException;
import com.yash.rest.customerservice.repository.CustomerRepository;


@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping(value = "/customers", consumes={MediaType.APPLICATION_JSON_VALUE ,MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(code = HttpStatus.CREATED)
	public Customer save(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	 }
	
	
	@GetMapping(value = "/customers/{customerId}", produces = {MediaType.APPLICATION_JSON_VALUE ,MediaType.APPLICATION_XML_VALUE}) 
	   public Customer findByCustomerId (@PathVariable Integer customerId){ 
	         return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));
	   }
	
	@DeleteMapping(value = "/customers/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Integer customerId){

		return customerRepository.findById(customerId).map(customer -> {
		customerRepository.delete(customer);
		return ResponseEntity.ok().build();
		}
        ).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));

	}
	
	@PutMapping(value = "/customers/{customerId}", consumes={MediaType.APPLICATION_JSON_VALUE ,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Customer> updateCustomer(@PathVariable Integer customerId,@RequestBody Customer newCustomer){
		
		return customerRepository.findById(customerId).map(customer -> {
			customer.setCustomerName(newCustomer.getCustomerName());
			customer.setDateofBirth(newCustomer.getDateofBirth());
			customer.setPhoneNumber(newCustomer.getPhoneNumber());
			customerRepository.save(customer);
			return ResponseEntity.ok(customer);
		}).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));
		
	}

}

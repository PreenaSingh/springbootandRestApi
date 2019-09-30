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

import com.yash.rest.customerservice.dao.Account;
import com.yash.rest.customerservice.dao.Customer;
import com.yash.rest.customerservice.exception.ResourceNotFoundException;
import com.yash.rest.customerservice.repository.AccountRepository;
import com.yash.rest.customerservice.repository.CustomerRepository;

@RestController
public class AccountController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@PostMapping(value = "/customers/{customerId}/accounts", consumes={MediaType.APPLICATION_JSON_VALUE ,MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(code = HttpStatus.CREATED)
	public Account save(@PathVariable Integer customerId,@RequestBody Account  account) {
		return customerRepository.findById(customerId).map(customer -> {
			account.setCustomer(customer);
			return accountRepository.save(account);
			
		}).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));

	 }
	
	
	
	@GetMapping(value = "/customers/{customerId}/accounts", produces={MediaType.APPLICATION_JSON_VALUE ,MediaType.APPLICATION_XML_VALUE})
	public Account getAccountInfo(@PathVariable Integer customerId){

	
		return accountRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] do not have any account"));

	}
	
	
	
	
	

	@DeleteMapping(value = "/customers/{customerId}/accounts/{accountId}")
	public ResponseEntity<?> deleteAccount(@PathVariable Integer customerId,@PathVariable Integer accountId){

		if (!customerRepository.existsById(customerId)) {
			throw new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found");
		}
		
		return accountRepository.findById(accountId).map(account ->{
			   accountRepository.delete(account);
			   return ResponseEntity.ok().build();
		       }).orElseThrow(() -> new ResourceNotFoundException("Account [accountId="+accountId+"] can't be found"));

	}
	
	@PutMapping(value = "/customers/{customerId}/accounts/{accountId}")
	public ResponseEntity<Account> updateAccount(@PathVariable Integer customerId,@PathVariable Integer accountId,@RequestBody Account newAccount){

		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));
		
		return accountRepository.findById(accountId).map(account ->{
			   newAccount.setCustomer(customer);
			   accountRepository.save(newAccount);
			   return ResponseEntity.ok(newAccount);
		       }).orElseThrow(() -> new ResourceNotFoundException("Account [accountId="+accountId+"] can't be found"));

		
	}

}

package com.yash.rest.customerservice.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.rest.customerservice.dao.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}

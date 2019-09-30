package com.yash.rest.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.rest.customerservice.dao.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}

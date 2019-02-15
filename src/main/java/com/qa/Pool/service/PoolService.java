package com.qa.Pool.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.qa.Pool.persistance.domain.Account;



public interface PoolService {
	
	List<Account> getAccounts();

    Account getAccount(Long id);

    Account addAccount(Account account);

    ResponseEntity<Object> deleteAccount(Long id);

    ResponseEntity<Object> updateAccount(Account account, Long id);
}

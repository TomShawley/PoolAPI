package com.qa.Pool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qa.Pool.persistance.domain.Account;
import com.qa.Pool.persistance.repository.PoolRepository;
import com.qa.Pool.util.exceptions.AccountNotFoundException;


@Service
public class PoolServiceImpl implements PoolService {

	@Autowired
    private PoolRepository repo;

    public List<Account> getAccounts() {
        return repo.findAll();
    }

    public Account getAccount(Long id) {
        Optional<Account> account = repo.findById(id);
        return account.orElseThrow(() -> new AccountNotFoundException(id.toString()));
    }

    public Account addAccount(Account account) {
        return repo.save(account);
    }

    @Override
    public ResponseEntity<Object> deleteAccount(Long id) {
        if(accountExists(id)){
            repo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Object> updateAccount(Account account, Long id) {
        if(accountExists(id)){
            account.setId(id);
            repo.save(account);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    private boolean accountExists(Long id){
        Optional<Account> accountOptional = repo.findById(id);
        return accountOptional.isPresent();
    }
}

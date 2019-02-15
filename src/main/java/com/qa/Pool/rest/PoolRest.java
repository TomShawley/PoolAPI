package com.qa.Pool.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.qa.Pool.persistance.domain.Account;
import com.qa.Pool.persistance.domain.SentAccount;
import com.qa.Pool.service.PoolService;





@CrossOrigin
@RequestMapping("${path.base}")
@RestController
public class PoolRest {

	@Autowired
    private PoolService service;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    
    
    @GetMapping("${path.getAccounts}")
    public List<Account> getAccounts() {
        return service.getAccounts();
    }

    @GetMapping("${path.getAccountById}")
    public Account getAccount(@PathVariable Long id) {
        return service.getAccount(id);
    }

    @DeleteMapping("${path.deleteAccount}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long id) {
        return service.deleteAccount(id);
    }

    @PutMapping("${path.updateAccount}")
    public ResponseEntity<Object> updateAccount(@RequestBody Account account, @PathVariable Long id) {
        return service.updateAccount(account, id);
    }
    
    @PostMapping("${path.createAccount}")
    public Account createAccount(@RequestBody Account account) {
        sendToQueue(account);
    	return service.addAccount(account);
    }
    

    private void sendToQueue(Account account){
        SentAccount accountToStore =  new SentAccount(account);
        jmsTemplate.convertAndSend("AccountQueue", accountToStore);
    }
}

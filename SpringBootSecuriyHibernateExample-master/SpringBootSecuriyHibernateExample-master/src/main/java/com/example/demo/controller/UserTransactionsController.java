package com.example.demo.controller;

import com.example.demo.dto.UserTransactionDto;
import com.example.demo.model.UserTransactions;
import com.example.demo.service.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserTransactionsController {

    private UserTransactionService service;

    @Autowired
    public UserTransactionsController(UserTransactionService service) {
        this.service = service;
    }

    @GetMapping("/transactions")
    @PreAuthorize("isAuthenticated()")
    public List<UserTransactions> getUserTransactionsByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();
            return service.getUserTransactionsByUserName(name);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/createNewTransaction")
    public void createNewTransaction(@RequestBody UserTransactionDto transactionDto) {

        String toUser = transactionDto.getToUser();
        Integer transactionVale = transactionDto.getTransactionVale();
        service.saveTransaction(toUser, transactionVale);

    }
}

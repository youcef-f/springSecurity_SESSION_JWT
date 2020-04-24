package com.example.demo.service;

import java.util.List;

public interface UserTransactionService {

    List getUserTransactionsByUserName(String name);
    void saveTransaction(String toUser, Integer transactionVale);
}

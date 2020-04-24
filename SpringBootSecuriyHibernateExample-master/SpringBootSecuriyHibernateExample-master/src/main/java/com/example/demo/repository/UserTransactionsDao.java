package com.example.demo.repository;

import com.example.demo.model.UserTransactions;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserTransactionsDao {
    List getUserTransactionList(@NotNull String name);
    void createTransaction(UserTransactions transaction);
}

package com.example.demo.service.impl;

import com.example.demo.exception.OutOfBudgetException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.UserTransactions;
import com.example.demo.repository.UserDao;
import com.example.demo.repository.UserTransactionsDao;
import com.example.demo.service.UserTransactionService;
import lombok.Data;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserTransactionsServiceImpl implements UserTransactionService {

    private UserTransactionsDao transactionsDao;
    private UserDao userDao;

    @Autowired
    public UserTransactionsServiceImpl(UserTransactionsDao transactionsDao, UserDao userDao) {
        this.transactionsDao = transactionsDao;
        this.userDao = userDao;
    }

    @Override
    public List getUserTransactionsByUserName(String name) {
        try {
            List transactionList = transactionsDao.getUserTransactionList(name);
            return transactionList;
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = OutOfBudgetException.class, propagation = Propagation.REQUIRED)
    public void saveTransaction(String toUser, Integer transactionValue) {
        User userGetter = userDao.findUserByName(toUser);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userSender = userDao.findUserByName(authentication.getName());
        if (transactionValue >= userSender.getBudget()) {
            throw new OutOfBudgetException(userSender.getUsername() + " is out of budget");
        }
        UserTransactions transaction = new UserTransactions();
        transaction.setToUser(userGetter.getUsername());
        transaction.setUser(userSender);
        transaction.setTransactionValue(transactionValue);
        handleUsersBudget(userSender, userGetter, transactionValue);
        transactionsDao.createTransaction(transaction);
        userDao.updateUser(userGetter);
        userDao.updateUser(userSender);
    }

    private void handleUsersBudget(User userSender, User userGetter, Integer value) {
        Integer getterBudget;
        Integer senderBudget;
        try {
            getterBudget = userGetter.getBudget() + value;
            senderBudget = userSender.getBudget() - value;
            userGetter.setBudget(getterBudget);
            userSender.setBudget(senderBudget);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException executeTransaction");
        }
    }

}

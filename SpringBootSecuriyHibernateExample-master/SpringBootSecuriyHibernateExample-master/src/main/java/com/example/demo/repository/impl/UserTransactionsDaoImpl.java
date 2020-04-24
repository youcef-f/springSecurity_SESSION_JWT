package com.example.demo.repository.impl;

import com.example.demo.model.UserTransactions;
import com.example.demo.repository.UserTransactionsDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
public class UserTransactionsDaoImpl implements UserTransactionsDao {

    private SessionFactory sessionFactory;

    @Autowired
    public UserTransactionsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List getUserTransactionList(@NotNull String name) {
        Session session = sessionFactory.getCurrentSession();
        List list = session.createQuery("from UserTransactions where user_sender = ?")
                .setParameter(0, name).list();
        return list;
    }

    @Override
    public void createTransaction(UserTransactions transaction) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(transaction);
    }
}
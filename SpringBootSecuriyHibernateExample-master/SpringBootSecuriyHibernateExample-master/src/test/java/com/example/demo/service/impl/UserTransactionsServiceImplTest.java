package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.model.UserTransactions;
import com.example.demo.repository.UserDao;
import com.example.demo.repository.UserTransactionsDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserTransactionsServiceImplTest {

    @InjectMocks
    private UserTransactionsServiceImpl service;

    @Mock
    private UserDao userDao;

    @Mock
    private UserTransactionsDao transactionsDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserTransactionsByUserName() {
        String name = "user";

        List<UserTransactions> transactions = new ArrayList<>();
        transactions.add(new UserTransactions());
        transactions.add(new UserTransactions());

        when(transactionsDao.getUserTransactionList(name)).thenReturn(transactions);
        List userTransactionsByUserName = service.getUserTransactionsByUserName(name);

        assertEquals(transactions,userTransactionsByUserName);
    }

    @Test
    @WithMockUser(username = "user", authorities = { "USER"})
    public void saveTransaction() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        String toUser = "user1";
        Integer trValue = 100;
        User user = new User();
        user = User.builder()
                .username("user")
                .password("pas")
                .budget(1000)
                .authorities(Arrays.asList(new UserRole(1, user, "USER")))
                .build();
        User user1 = new User();
        user1 = User.builder()
                .username("user1")
                .password("pas")
                .budget(1000)
                .authorities(Arrays.asList(new UserRole(2, user1, "USER")))
                .build();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("user");
        when(userDao.findUserByName(toUser)).thenReturn(user1);
        when(userDao.findUserByName("user")).thenReturn(user);
        service.saveTransaction(toUser, trValue);
        verify(userDao, times(1)).findUserByName(toUser);
        verify(userDao, times(1)).findUserByName("user");

        UserTransactions transaction = new UserTransactions();
        transaction.setUser(user);
        transaction.setToUser(user1.getUsername());
        transaction.setTransactionValue(trValue);

        verify(transactionsDao, times(1))
                .createTransaction(transaction);
        verify(userDao, times(1)).updateUser(user);
        verify(userDao, times(1)).updateUser(user1);
    }
}
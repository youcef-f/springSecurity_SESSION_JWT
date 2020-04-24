package com.example.demo.controller;

import com.example.demo.dto.UserTransactionDto;
import com.example.demo.model.User;
import com.example.demo.model.UserTransactions;
import com.example.demo.service.UserService;
import com.example.demo.service.UserTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTransactionsControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @InjectMocks
    private UserTransactionsController controller;

    @Mock
    private UserService userService;

    @Mock
    private UserTransactionService trService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @WithMockUser(username = "user1", authorities = { "USER" })
    public void getUserTransactionsByUserName() throws Exception {
        List<UserTransactions> list = new ArrayList<>(Arrays.asList(
                new UserTransactions(1, User.builder().username("user1").build()),
                new UserTransactions(2, User.builder().username("user1").build())
        ));

        when(trService.getUserTransactionsByUserName("user1")).thenReturn(list);

        mockMvc.perform(get("/transactions").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(trService).getUserTransactionsByUserName("user1");

    }

    @Test
    @WithMockUser(username = "user1", authorities = { "USER" })
    public void createNewTransaction() throws Exception {
        String toUser = "to";
        UserTransactionDto dto = new UserTransactionDto();
        dto.setToUser(toUser);
        Integer transactionVale = 100;
        dto.setTransactionVale(transactionVale);


        mockMvc.perform(post("/createNewTransaction")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(mapper.writeValueAsBytes(dto)))
                .andExpect(status().isOk());

        verify(trService).saveTransaction(toUser, transactionVale);
    }
}
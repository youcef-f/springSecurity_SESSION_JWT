package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @WithAnonymousUser
    public void saveUser() throws Exception {
        String name = "name";
        String password = "password";
        UserDto userDto = new UserDto();
        userDto.setUsername(name);
        userDto.setPassword(password);
        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(mapper.writeValueAsBytes(userDto)))
                .andExpect(status().isOk());
        verify(service, times(1)).saveUser(name, password);

    }

    @Test
    @WithMockUser(username = "user", authorities = { "USER"})
    public void getUserAuth() throws Exception {
        User user = User.builder().username("user").build();
        when(service.getUserByName("user")).thenReturn(user);

        mockMvc.perform(get("/person").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    @WithUserDetails(value = "ADMIN")
    public void getAllUsers() throws Exception {

        User user1 = User.builder().username("user1").build();
        User user2 = User.builder().username("user2").build();

        List<User> list = new ArrayList<>(Arrays.asList(user1, user2));

        when(service.findAllUsers()).thenReturn(list);

        mockMvc.perform(get("/userList").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").exists())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].username").exists())
                .andExpect(jsonPath("$[1].username").value("user2"));

    }

    @Test
    @WithUserDetails(value = "ADMIN")
    public void findUserByName() throws Exception {
        String name = "user1";
        User user1 = User.builder().username("user1").build();
        when(service.getUserByName(name)).thenReturn(user1);

        mockMvc.perform(get("/findUser?name="+name).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    @WithUserDetails(value = "ADMIN")
    public void lockUser() throws Exception {
        String name = "user1";
        mockMvc.perform(post("/lock?name="+name)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(name))
                .andExpect(status().isOk());
        verify(service).lockUser(name);
    }
}
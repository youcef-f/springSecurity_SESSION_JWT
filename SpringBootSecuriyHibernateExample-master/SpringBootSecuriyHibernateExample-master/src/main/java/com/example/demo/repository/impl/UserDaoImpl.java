package com.example.demo.repository.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserDao;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    @Override
    public User findUserByName(String name) throws UserNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, name);
        if (user == null) throw new UserNotFoundException("User : " + name + " was not found");
        return user;
    }

    @Override
    public void saveUser(@NotNull String username, @NotNull String password) {
        Session session = sessionFactory.getCurrentSession();
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setBudget(100);
        List<UserRole> authorities = new ArrayList<>();
        UserRole role = new UserRole();
        role.setAuthority("USER");
        role.setUser(user);
        user.setAuthorities(authorities);
        session.persist(role);
    }

    @Override
    public void saveAdmin(@NotNull String username, @NotNull String password) {
        Session session = sessionFactory.getCurrentSession();
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setBudget(10000);
        List<UserRole> authorities = new ArrayList<>();
        UserRole role = new UserRole();
        role.setAuthority("ADMIN");
        role.setUser(user);
        user.setAuthorities(authorities);
        session.saveOrUpdate(role);
    }

    @Override
    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public List<User> findAllUsers() {
        return sessionFactory.getCurrentSession().createQuery("select u from User u join UserRole role on u.username=role.user where not role.authority='ADMIN'").list();

    }
}

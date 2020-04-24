package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Id
    @Column(name = "username", unique = true)
    @NotNull
    private String username;

    @Column(name = "password", nullable = false)
    @NotNull
    @JsonIgnore
    private String password;

    @Column(name = "budget")
    private Integer budget;

    @Column(name = "non_expired")
    @JsonIgnore
    private boolean accountNonExpired;

    @Column(name = "non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    @JsonIgnore
    private boolean credentialsNonExpired;

    @Column(name = "enabled")
    @JsonIgnore
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UserRole> authorities = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<UserTransactions> transactionList = new ArrayList<>();

    public User(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}


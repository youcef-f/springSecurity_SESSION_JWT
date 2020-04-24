package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "user_transactions")
public class UserTransactions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "value")
    private Integer transactionValue;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_sender")
    @JsonBackReference
    private User user;

    @Column(name = "recipient")
    private String toUser;

    @Column(name = "is_interrupted")
    private boolean isInterrupted;

    @Column(name = "date")
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date beginDate;

    public UserTransactions() {
        this.beginDate = new Date();
        this.isInterrupted = false;
    }

    public UserTransactions(Integer transactionValue, User user) {
        this.transactionValue = transactionValue;
        this.user = user;
    }
}

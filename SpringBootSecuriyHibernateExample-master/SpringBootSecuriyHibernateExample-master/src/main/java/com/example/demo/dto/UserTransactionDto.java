package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserTransactionDto {

    @NotNull
    private String toUser;
    @NotNull
    private Integer transactionVale;

}

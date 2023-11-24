package com.microservice.service;

import com.microservice.dto.SignUpDto;
import com.microservice.entity.User;

public interface UserService {
    User getBalance(String accountNumber);

    String saveWithdrawAmount(Double amount, String accountNumber);

    String saveAddAmount(Double amount, String accountNumber);

    String transferMoney(Double amount, String senderAccountNumber, String receiverAccountNumber);

    String addUser(SignUpDto signUpDto);

    String login(String username, String password);
}

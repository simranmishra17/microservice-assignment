package com.microservice.service.impl;

import com.microservice.dto.SignUpDto;
import com.microservice.entity.User;
import com.microservice.repository.UserRepository;
import com.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getBalance(String accountNumber) {
        return userRepository.findByAccountNumber(accountNumber);
    }

    @Override public String saveWithdrawAmount(Double amount, String accountNumber) {
        User user = userRepository.findByAccountNumber(accountNumber);
        String message = null;
        if (user == null) {
            message = "Wrong Account number";
        } else {
            Double balance = user.getBalance();
            if (balance < amount) {
                message = "Your account does not have enough balance to process the transaction";
            } else {
                user.setBalance(balance - amount);
                userRepository.save(user);
                message = "Success";
            }
        }
        return message;
    }

    @Override
    public String saveAddAmount(Double amount, String accountNumber) {
        User user = userRepository.findByAccountNumber(accountNumber);
        String message = null;
        if (user == null) {
            message = "Wrong Account number";
        } else {
            Double balance = user.getBalance();
            user.setBalance(balance + amount);
            userRepository.save(user);
            message = "Success";
        }
        return message;
    }

    @Override
    public String transferMoney(Double amount, String senderAccountNumber, String receiverAccountNumber) {
        User sender = userRepository.findByAccountNumber(senderAccountNumber);
        User receiver = userRepository.findByAccountNumber(receiverAccountNumber);
        String message;
        if (sender == null) {
            message = "Sender's Account number is not Valid";
        } else if (receiver == null) {
            message = "Receiver's Account number is not Valid";
        } else {
            Double balance = sender.getBalance(); //800
            if (amount > balance) {
                message = "Your account does not have enough balance to process the transaction";
            } else {
                sender.setBalance(balance - amount);
                userRepository.save(sender);
                receiver.setBalance(receiver.getBalance() + amount);
                userRepository.save(receiver);
                message = "Success";
            }
        }
        return message;
    }

    @Override public String addUser(SignUpDto signUpDto) {
        String message;
        User user = userRepository.findByFirst_nameAndLast_nameAndPhone_number(signUpDto.getFirst_name(), signUpDto.getLast_name(), signUpDto.getPhone_number());
        if (user != null) {
            message = "Account already exists with this name and phone number";
        } else {
            User newUser = new User();
            newUser.setBalance(0);
            newUser.setEmail(signUpDto.getEmail());
            newUser.setFirst_name(signUpDto.getFirst_name());
            newUser.setLast_name(signUpDto.getLast_name());
            newUser.setPhone_number(signUpDto.getPhone_number());
            newUser.setAddress(signUpDto.getAddress());
            newUser.setPassword("NewPassword1");
            Random random = new Random();
            int uniqueNumber = random.nextInt(900) + 100;
            newUser.setAccountNumber(String.valueOf(uniqueNumber));
            newUser.setUsername(signUpDto.getFirst_name() + "." + signUpDto.getLast_name()+uniqueNumber);
            userRepository.save(newUser);
            message = "Your account have been successfully created " + "/n" + "accountNumber: " + user.getAccountNumber() + "/n" + "username: " + user.getUsername() + "/n" + "password: " + user.getPassword();
        }
        return message;
    }

    @Override public String login(String username, String password) {
        User user = (User) userRepository.findByUsernameAndPassword(username,password);
        return user==null?"Wrong credentials":"success";
    }

}

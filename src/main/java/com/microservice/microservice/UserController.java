package com.microservice.microservice;

import com.microservice.dto.SignUpDto;
import com.microservice.entity.User;
import com.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping(value = "/balance/{account_number}")
    public ResponseEntity<User> getBalance(@PathVariable("account_number") String account_number) {
        User user = userService.getBalance(account_number);
        user.setPassword("********");
        return ResponseEntity.ok(user);
    }

    @PostMapping("/withdraw/{account_number}/{amount}")
    public ResponseEntity<String> withdrawAmount(@PathVariable("amount") Double amount,@PathVariable("account_number") String account_number){
        String message = userService.saveWithdrawAmount(amount,account_number);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/addAmount/{account_number}/{amount}")
    public ResponseEntity<String> addAmount(@PathVariable("amount") Double amount,@PathVariable("account_number") String account_number){
        String message = userService.saveAddAmount(amount,account_number);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/transfer/{senderAccountNumber}/{receiverAccountNumber}/amount")
    public ResponseEntity<String> sendMoney(@PathVariable("amount") Double amount,@PathVariable("senderAccountNumber") String senderAccountNumber,
                                            @PathVariable("receiverAccountNumber") String receiverAccountNumber){
        String message = userService.transferMoney(amount, senderAccountNumber, receiverAccountNumber);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto){
        String message = userService.addUser(signUpDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping(value = "/login/{username}/{password}")
    public ResponseEntity<String> getBalance(@PathVariable("username") String username, @PathVariable("password") String password) {
        String message = userService.login(username,password);
        return ResponseEntity.ok(message);
    }

    @GetMapping()
    public String Message() {
        return "hgi";
    }
}

package com.message.app.controller;


import com.message.app.SecurityConfig;
import com.message.app.dto.UserDto;
import com.message.app.dto.UserLoginDto;
import com.message.app.servies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public String registerUser(@RequestBody UserDto userDto) {
        System.out.println(userDto.toString());
        return userService.register(userDto);
    }
    @PostMapping(value = "/test")
    public void test(@RequestParam String p1, @RequestParam String p2)
    {
        String pass1 = securityConfig.encode(p1);
        String pass2 = securityConfig.encode(p2);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        if(encoder.matches(p1 , pass2 )){
            System.out.println("Password Matched");
        }
        else {
            System.out.println("Invalid Password");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto loginDto) {
        String loginResult = userService.login(loginDto);
        if (loginResult.equals("Login Successful!")) {
            return ResponseEntity.ok(loginResult);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResult);
        }
    }
}
package com.message.app.controller;

import com.message.app.dto.MessageDto;
import com.message.app.dto.UserDto;
import com.message.app.dto.UserLoginDto;
import com.message.app.servies.UserService;
import com.message.app.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = Constant.ADD_PATH)
    public ResponseEntity<MessageDto> registerUser(@RequestBody UserDto userDto) {
        logger.info(userDto.toString());
        return userService.register(userDto);
    }

    @PostMapping(value = Constant.LOGIN_PATH)
    public ResponseEntity<MessageDto> loginUser(@RequestBody UserLoginDto loginDto) {
        MessageDto messageDto = new MessageDto();
        try {
            ResponseEntity<MessageDto> loginStatus = userService.login(loginDto);
            logger.info("Login Status: {}", loginStatus);
            if (Constant.SUCCESS.equals(Objects.requireNonNull(loginStatus.getBody()).getResponseMessage())) {
                messageDto.setResponseMessage(Constant.LOGIN_SUCCESS);
                return ResponseEntity.ok(messageDto);
            } else {
                messageDto.setResponseMessage(Constant.FAILED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageDto);
            }
        } catch (Exception e) {
            logger.error("Error creating session: {}", e.getMessage());
            messageDto.setResponseMessage(Constant.LOGIN_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageDto);
        }
    }
}
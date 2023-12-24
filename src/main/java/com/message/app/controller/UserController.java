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

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = Constant.ADD_PATH)
    public MessageDto registerUser(@RequestBody UserDto userDto) {
        logger.info(userDto.toString());
        return userService.register(userDto);
    }

    @PostMapping(value = Constant.LOGIN_PATH)
    public MessageDto loginUser(@RequestBody UserLoginDto loginDto) {
        MessageDto messageDto = new MessageDto();
        try {
            messageDto = userService.login(loginDto);
            if (Constant.SUCCESS.equals(messageDto.getResponseMessage())) {
                messageDto.setResponseMessage(Constant.LOGIN_SUCCESS);
            } else {
                messageDto.setResponseMessage(Constant.FAILED);
            }
        } catch (Exception e) {
            logger.error("Error creating session: {}", e.getMessage());
            messageDto.setResponseMessage(Constant.LOGIN_ERROR);
        }
        return messageDto;
    }
}
package com.message.app.controller;


import com.message.app.configration.SecurityConfig;
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
    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserService userService;

    @PostMapping(value = Constant.ADD_PATH)
    public String registerUser(@RequestBody UserDto userDto) {
        logger.info(userDto.toString());
        return userService.register(userDto);
    }


    @PostMapping(value = Constant.LOGIN_PATH)
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDto loginDto) {
        try {
            String loginResult = userService.login(loginDto);
            if (loginResult.equals(Constant.SUCCESS)) {
                return ResponseEntity.ok(loginResult);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResult);
            } }
        catch (Exception e) {
            logger.error("Error creating session: {}", e.getMessage());
        }
    }
// Hard coded responce:LoginSuccessful
}
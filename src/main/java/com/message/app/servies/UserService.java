package com.message.app.servies;

import com.message.app.configration.SecurityConfig;
import com.message.app.dto.UserDto;
import com.message.app.dto.UserLoginDto;
import com.message.app.model.User;
import com.message.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class UserService {
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @Autowired
    private ListOperations<String, Object> redisListOperations;

    @Autowired
    private RedisListService redisListService;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    public String register(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return "Email already exists. Please use a different email.";
        }
        User user = new User();

        String password=securityConfig.encode(userDto.getPassword());
        System.out.println(password);
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(password);
        userRepository.save(user);
        return "User Registered!";
    }


    public String login(UserLoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        String response=null;
        if (user != null && securityConfig.passwordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
            try {
                Session session = sessionRepository.createSession();
                String sessionData = "User: " + loginDto.getEmail();
                redisListService.pushDataToList("user:sessions:" + session.getId(), sessionData);
                logger.info("Session created and data pushed to Redis for user: {}", loginDto.getEmail());
                System.out.println("Session ID: " + session.getId());
                response = "Login Successful and Session Created!";
            } catch (Exception e) {
                logger.error("Error creating session: {}", e.getMessage());
                response = "An error occurred during login. Please try again later.";
            }
        }
    return response;
    }

}




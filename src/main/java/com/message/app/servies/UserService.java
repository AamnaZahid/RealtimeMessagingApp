package com.message.app.servies;

import com.message.app.configration.SecurityConfig;
import com.message.app.dto.MessageDto;
import com.message.app.dto.UserDto;
import com.message.app.domain.UserDomain;
import com.message.app.dto.UserLoginDto;
import com.message.app.repository.UserRepository;
import com.message.app.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
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

    public MessageDto register(UserDto userDto) {
        MessageDto messageDto = new MessageDto();
        try {
            if (userDto.getEmail() != null && userDto.getUserName() != null && userDto.getPassword() != null) {
                if (userRepository.existsByEmail(userDto.getEmail())) {
                    messageDto.setResponseMessage(Constant.ALREADY_EXIST);
                } else {
                    UserDomain userDomain = new UserDomain();
                    String password = securityConfig.encode(userDto.getPassword());
                    userDomain.setUserName(userDto.getUserName());
                    userDomain.setEmail(userDto.getEmail());
                    userDomain.setPassword(password);
                    userRepository.save(userDomain);
                    messageDto.setResponseMessage(Constant.REGISTERED);
                }
            } else {
                messageDto.setResponseMessage(Constant.FAILED);
            }
        } catch (Exception e) {
            logger.error("Error creating session: {}", e.getMessage());
            messageDto.setResponseMessage(Constant.FAILED);
        }
        return messageDto;
    }

    public MessageDto login(UserLoginDto loginDto) {
        com.message.app.domain.UserDomain userDomain = userRepository.findByEmail(loginDto.getEmail());
        MessageDto messageDto = new MessageDto();
        if (userDomain != null && securityConfig.passwordEncoder().matches(loginDto.getPassword(), userDomain.getPassword())) {
            try {
                Session session = sessionRepository.createSession();
                String sessionData = "User: " + loginDto.getEmail();
                redisListService.pushDataToList("user:sessions:" + session.getId(), sessionData);
                logger.info("Session created and data pushed to Redis for user: {}", loginDto.getEmail());
                logger.info("Session ID: " + session.getId());
                messageDto.setResponseMessage(Constant.SUCCESS);
            } catch (Exception e) {
                logger.error("Error creating session: {}", e.getMessage());
                messageDto.setResponseMessage(Constant.FAILED);
            }
        }
        return messageDto;
    }
}
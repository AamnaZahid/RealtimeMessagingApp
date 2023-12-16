package com.message.app.servies;

import com.message.app.SecurityConfig;
import com.message.app.dto.UserDto;
import com.message.app.dto.UserLoginDto;
import com.message.app.model.User;
import com.message.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Service;

import javax.websocket.Session;


@Service
public class UserService {
    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;
   // private static final Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);
   @Autowired
   private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

   @Autowired
   private ListOperations<String, String> redisListOperations;

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

        if (user != null && securityConfig.passwordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
            Session session = sessionRepository.createSession();

            String sessionData = "User: " + loginDto.getEmail();
            redisListOperations.leftPush("user:sessions:" + session.getId(), sessionData);

            return "Login Successful and Session Created!";
        } else {

            return "Invalid credentials. Please check your email and password.";
        }
    }
//null pointer exception and try catch  ; one return statement
}




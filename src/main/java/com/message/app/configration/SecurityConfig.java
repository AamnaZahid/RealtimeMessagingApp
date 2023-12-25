package com.message.app.configration;

import com.message.app.servies.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SecurityConfig {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encode(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder=null;
        try {
            bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        } catch (Exception e) {
            logger.error("Error creating session: {}", e.getMessage());
        }
        return bCryptPasswordEncoder.encode(password);
    }
}
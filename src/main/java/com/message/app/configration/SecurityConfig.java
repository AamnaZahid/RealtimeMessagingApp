package com.message.app.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public String encode(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(10,new SecureRandom());

        return bCryptPasswordEncoder.encode(password);
    }


}

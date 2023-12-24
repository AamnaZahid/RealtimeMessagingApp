package com.message.app.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class UserDomain {
    @Id
    private String userId;
    private String userName;
    private String email;
    private String password;
    public String getEmail() {
        return email != null ? email : "";
    }
}
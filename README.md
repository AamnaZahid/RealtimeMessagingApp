# Real-Time Messaging App Documentation

## 1. Introduction
The Real-Time Messaging App is designed to provide user registration and login functionalities using Spring Boot. MongoDB is used for data storage, while Redis manages session information.

## 2. Implemented Functionalities

### 2.1. User Registration Functionality
**Description:**
The `UserService` implements user registration by validating and storing user data in the MongoDB database.

**Steps Taken:**
- Checks if the provided email exists in the database.
- Encrypts the password using the `SecurityConfig` class.
- Creates a new `User` object and saves it to the MongoDB repository.

### 2.2. User Login Functionality
**Description:**
The `UserService` manages user login by verifying credentials, creating a session using Redis, and handling session data.

**Steps Taken:**
- Finds the user by the provided email in the UserRepository.
- Compares the provided password with the stored encrypted password.
- Creates a session using Spring Session and stores session data in Redis.

## 3. Technology Stack and Implementation Details

### Technologies Used:
- Spring Boot
- MongoDB for data storage
- Redis for session management

### Implementation Overview:
- Integration of MongoDB for user data storage.
- Integration of Redis for session management using Spring Session.

### Dependencies and Configurations:
- Configuration of `RedisConfig` to set up Redis connections and operations.
- Configuration of `SecurityConfig` for password encoding using BCrypt.

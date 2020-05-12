package com.oracle.studies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IEmailRepository emailRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    
    public void run() {
        Iterable<User> users = userRepository.findAll();
        emailRepository.saveAllUsers(users);
    }
}

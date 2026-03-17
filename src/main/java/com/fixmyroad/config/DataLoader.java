package com.fixmyroad.config;

import com.fixmyroad.model.User;
import com.fixmyroad.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com")==null) {

                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");

                userRepository.save(admin);

                System.out.println("✅ Admin created");
            }
        };
    }
}
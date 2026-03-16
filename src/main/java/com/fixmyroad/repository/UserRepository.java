package com.fixmyroad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fixmyroad.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
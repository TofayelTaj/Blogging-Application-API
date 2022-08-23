package com.example.bloggingapplicationapi.repositories;

import com.example.bloggingapplicationapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

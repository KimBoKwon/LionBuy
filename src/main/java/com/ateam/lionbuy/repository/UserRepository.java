package com.ateam.lionbuy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ateam.lionbuy.entity.User_info;

public interface UserRepository extends JpaRepository<User_info, Long>{
    
    @Query("select u from User_info u where u.user_email=:user_email")
    Optional<User_info> getInfo(@Param("user_email") String user_email);
}

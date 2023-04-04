package com.ateam.lionbuy.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User_info {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_num;
    
    @Column(unique = true)
    private String user_email;

    private String user_pw;
    private String user_name;
    private String user_gender;
    private String user_birth;
    private LocalDateTime join_date;
    
    @PrePersist
    public void PrePersist() {
        join_date = LocalDateTime.now();
    }
}

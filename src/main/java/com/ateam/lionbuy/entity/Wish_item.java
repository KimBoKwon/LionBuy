package com.ateam.lionbuy.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Wish_item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_num", referencedColumnName = "user_num")
    private User_info user_info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pd_name", referencedColumnName = "pd_name")
    private Product product;

    private Long priority;
    private LocalDateTime choice_date;

    @PrePersist
    public void PrePersist() {
        choice_date = LocalDateTime.now();
    }

}

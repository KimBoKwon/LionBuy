package com.ateam.lionbuy.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long user_num;
    private String user_email;
    private String user_pw;
    private String user_name;
    private String user_gender;
    private String user_birth;
    private LocalDateTime join_date;
}

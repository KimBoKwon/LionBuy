package com.ateam.lionbuy.service;

import java.util.List;

import com.ateam.lionbuy.dto.UserDTO;
import com.ateam.lionbuy.entity.User_info;

public interface UserService {
    
    String join(UserDTO userdto);

    String login(String user_email);

    List<UserDTO> user_all();

    default User_info user_build_entity(UserDTO userdto) {
        User_info user_info = User_info.builder()
            .user_email(userdto.getUser_email())
            .user_pw(userdto.getUser_pw())
            .user_name(userdto.getUser_name())
            .user_gender(userdto.getUser_gender())
            .user_birth(userdto.getUser_birth())
            .build();
        return user_info;
    }

    default UserDTO user_build_dto(User_info user_info) {
        UserDTO userDTO = UserDTO.builder()
            .user_num(user_info.getUser_num())
            .user_email(user_info.getUser_email())
            .user_pw(user_info.getUser_pw())
            .user_name(user_info.getUser_name())
            .user_gender(user_info.getUser_gender())
            .user_birth(user_info.getUser_birth())
            .join_date(user_info.getJoin_date())
            .build();
        return userDTO;
    }
}

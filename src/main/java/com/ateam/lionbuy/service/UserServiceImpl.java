package com.ateam.lionbuy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ateam.lionbuy.dto.UserDTO;
import com.ateam.lionbuy.entity.User_info;
import com.ateam.lionbuy.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository uRepository;

    @Override
    public String join(UserDTO userdto) {
        User_info user_info = user_build_entity(userdto);
        uRepository.save(user_info);    
        return "성공";
    }

    @Override
    public String login(String user_email) {
        Optional<User_info> user_info = uRepository.getInfo(user_email);
        if(user_info.isPresent()) {
            return "성공";
        }else {
            return "실패";
        }
    }

    @Override
    public List<UserDTO> user_all() {
        List<User_info> alluser_entity = uRepository.findAll();
        List<UserDTO> alluser_dto = new ArrayList<UserDTO>();
        for (User_info alluser : alluser_entity) {
            UserDTO userDTO = user_build_dto(alluser);
            alluser_dto.add(userDTO);
        }
        return alluser_dto;
    }
}

package com.ateam.lionbuy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ateam.lionbuy.dto.UserDTO;
import com.ateam.lionbuy.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService uService;
    
    @PostMapping(value = "/join")
    public ResponseEntity<String> user_join(@RequestBody UserDTO userdto) {
        uService.join(userdto);
        return ResponseEntity.ok().body("성공");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> user_login(@RequestBody UserDTO userdto) {
        String result = uService.login(userdto.getUser_email());
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> user_all() {
        List<UserDTO> userDTO = uService.user_all();
        return ResponseEntity.ok().body(userDTO);
    }
}

package com.salinas.test.usermanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salinas.test.usermanager.model.User;
import com.salinas.test.usermanager.service.UserService;

@RequestMapping(value = "/api/v1/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllUsers(){
        try {
            Map<String, List<User>> resp = new HashMap<>();
            resp.put("payload", userService.getAllUsers());
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "Hubo un error al generar la lista de usuarios");
            resp.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }
}

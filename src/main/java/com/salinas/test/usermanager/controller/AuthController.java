package com.salinas.test.usermanager.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salinas.test.usermanager.dto.AuthResponseDto;
import com.salinas.test.usermanager.dto.LoginUserDto;
import com.salinas.test.usermanager.dto.RegisterUserDto;
import com.salinas.test.usermanager.model.User;
import com.salinas.test.usermanager.security.JwtConfig;
import com.salinas.test.usermanager.service.AuthService;

import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthController{

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public <T> ResponseEntity<Map<String,T>> register(@Valid @RequestBody RegisterUserDto registerUserDto){

        Map<String, T> resp = new HashMap<>();
        
        try {
            User registerdUser = authService.register(registerUserDto);
            resp.put("message", (T)"Usuario registrado con éxito");
            resp.put("payload", (T)registerdUser);

            return ResponseEntity.status(201).body(resp);
        } catch (Exception e) {
            resp.put("error", (T)"Hubo un error al validar sus datos");
            resp.put("message", (T)e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    @PostMapping("/login")
    public <T> ResponseEntity<Map<String, T>> login(@Valid @RequestBody LoginUserDto loginUserDto){
        
        Map<String, T> resp = new HashMap<>();
            
        try {
            User authenticatedUser = authService.login(loginUserDto);
            String jwtToken = jwtConfig.buildToken(authenticatedUser.getRoles() ,authenticatedUser);
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwtToken);
            authResponseDto.setExpiresIn(jwtConfig.getJwtExpiration());

            resp.put("message", (T)"Logueado con éxito");
            resp.put("payload", (T)authResponseDto);

            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            resp.put("error", (T) "Error al iniciar sesión");
            resp.put("message", (T) e.getMessage());

            return ResponseEntity.ok(resp);
        }
    }


}

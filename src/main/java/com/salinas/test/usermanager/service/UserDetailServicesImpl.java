package com.salinas.test.usermanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salinas.test.usermanager.model.User;
import com.salinas.test.usermanager.repository.UserRepository;

@Service
public class UserDetailServicesImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + username));
        
        return user;
    }

}

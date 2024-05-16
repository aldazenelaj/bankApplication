package com.bank.application.service;

import com.bank.application.entity.User;
import com.bank.application.repository.RoleRepository;
import com.bank.application.repository.UserRepository;
import com.bank.application.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  RoleRepository roleRepository;

    public User registerOrUpdate(RegisterUserRequest input) {
        User user = new User();

        user.setId(input.getId());
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setName(input.getName());
        user.setLastName(input.getLastName());
        user.setCreationTime(Timestamp.valueOf(LocalDateTime.now()));
        user.setRole(roleRepository.findRoleByRolename(input.getRole().getRolename()));

        return userRepository.save(user);
    }

    public Boolean delete(String username){
        if(userRepository.deleteUserByUsername(username) != 0)
            return true;
        return false;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getRolename())
                .build();
        return userDetails;
    }
}


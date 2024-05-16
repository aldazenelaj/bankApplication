package com.bank.usermanagement.mapper;

import com.bank.usermanagement.dto.RoleDto;
import com.bank.usermanagement.dto.UserDto;
import com.bank.usermanagement.entity.Role;
import com.bank.usermanagement.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            protected void configure() {
                map().setId(source.getId());
                map().setUsername(source.getUsername());
                map().setLastName(source.getLastName());
                map().setPassword(source.getPassword());
                map().setCreationTime(source.getCreationTime());
                map().setEmail(source.getEmail());
                map(source.getRole(), destination.getRole());
            }

        });
        modelMapper.addMappings(new PropertyMap<Role, RoleDto>() {
            protected void configure() {
                map().setId(source.getId());
                map().setRolename(source.getRolename());
            }

        });
        return modelMapper;
    }
}

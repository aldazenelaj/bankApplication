package com.bank.transaction.mapper;

import com.bank.transaction.dto.*;
import com.bank.transaction.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<CardTypeEntity, CardTypeDto>() {
            protected void configure() {
                map().setId(source.getId());
                map().setType(source.getType());
            }

        });
        modelMapper.addMappings(new PropertyMap<CountryEntity, CountryDto>() {
            protected void configure() {
                map().setId(source.getId());
                map().setCountry(source.getCountry());
            }

        });
        modelMapper.addMappings(new PropertyMap<BankEntity, BankDto>() {
            protected void configure() {
                map().setId(source.getId());
                map().setBankCode(source.getBankCode());
            }

        });

        modelMapper.addMappings(new PropertyMap<BankAccountTypeEntity, BankTypeDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setType(source.getType());
            }

        });
        modelMapper.addMappings(new PropertyMap<CurrencyEntity, CurrencyDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setCurrency(source.getCurrency());
            }
        });
        modelMapper.addMappings(new PropertyMap<BankAccountApplicationEntity, BankAccountApplicationDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                //   map().setCurrency(source.getCurrency());
                map().setStatus(source.getStatus());
                map().setUserId(source.getUserId());
                //     map().setBankAccountType(source.getBankAccountType());
                map().setCreateDate(source.getCreateDate());
            }
        });
        modelMapper.addMappings(new PropertyMap<BankAccountEntity, BankAccountDto>() {
            protected void configure() {
                map().setId(source.getId());
                //        map().setCurrency(destination.getCurrency());
                map().setStatus(source.getStatus());
                map().setUserId(source.getUserId());
                //        map().setBankAccountType(destination.getBankAccountType());
                map().setIban(source.getIban());
                map().setBalance(source.getBalance());
                map().setInteres(source.getInteres());
                map().setCreateDate(source.getCreateDate());
            }
        });

        modelMapper.addMappings(new PropertyMap<CardApplicationEntity, CardApplicationDto>() {
            protected void configure() {
                map().setId(source.getId());
                map().setStatus(source.getStatus());
                map().setUserId(source.getUserId());
                map().setMonthlySalary(source.getMonthlySalary());
                map().setCreateDate(source.getCreateDate());
                map().setInteres(source.getInteres());
                map().setLimitAmount(source.getLimitAmount());
            }
        });
        return modelMapper;
    }
}

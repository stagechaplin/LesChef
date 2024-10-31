package com.example.LesChef.service;

import com.example.LesChef.Repository.CustomerRepository;
import com.example.LesChef.dto.AddCustomerRequest;
import com.example.LesChef.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor // final 자동 생성자

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화

    //회원 생성 Create
    public void save(AddCustomerRequest dto){
        customerRepository.save(Customer.builder()
                .id(dto.getId())
                .password(bCryptPasswordEncoder.encode(dto.getPw()))
                .name(dto.getName())
                .nickname(dto.getNickname())
                .tel(dto.getTel())
                .build());
    }
}

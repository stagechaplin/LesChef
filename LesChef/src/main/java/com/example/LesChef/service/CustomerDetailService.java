package com.example.LesChef.service;

import com.example.LesChef.Repository.CustomerRepository;
import com.example.LesChef.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor

public class CustomerDetailService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    // 회원가입시 아이디 중복 확인
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다." + id));
        return new User(customer.getId(), customer.getPw(), Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
    }
}

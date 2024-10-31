package com.example.LesChef.controller;

import com.example.LesChef.Repository.CustomerRepository;
import com.example.LesChef.dto.AddCustomerRequest;
import com.example.LesChef.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j //loginfo 사용
@RequiredArgsConstructor
@Controller
public class CustomerApiController {
//회원 가입
    private final CustomerRepository customerRepository; //DB에서 아이디 확인
    private final CustomerService customerService;

    //기존 아이디 존재 여부 확인
    @PostMapping("/signup")
    public String signup(AddCustomerRequest request){
        log.info(" 진입여부 확인");
        if(customerRepository.findById(request.getId()).orElse(null) != null){
            log.info("로그인 실패");
            return "redirect:/main";
        }
        customerService.save(request);
        log.info("회원가입 성공");
        return "redirect:/main";
    }
}

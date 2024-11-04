package com.example.LesChef.service;

import com.example.LesChef.Repository.CustomerRepository;
import com.example.LesChef.dto.AddCustomerRequest;
import com.example.LesChef.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor // final 자동 생성자

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화

    //회원 생성 시 db의 값을 확인 후 없으면 Create
    public void save(AddCustomerRequest dto){
        if(customerRepository.findById(dto.getId()).orElse(null) != null){
            log.info("회원가입 실패");
        }
            else {
            customerRepository.save(Customer.builder()
                    .id(dto.getId())
                    .password(bCryptPasswordEncoder.encode(dto.getPw()))
                    .name(dto.getName())
                    .nickname(dto.getNickname())
                    .tel(dto.getTel())
                    .build());
        }
    }
    @Transactional
    public void edit(AddCustomerRequest dto, MultipartFile file, Customer currentUser){
        String currentId = currentUser.getId();
        log.info("현재 유저ID:" + currentId);
        Customer customer =customerRepository.findById(currentId).orElse(null);
        if(customer != null){
            try{
                if("".equals(file.getOriginalFilename())){

                    String fileName = currentUser.getCustomerImg();
                    String filePath = " " + fileName;
                }
                    else {
                        String filePath = " " + file.getOriginalFilename();
                        File dest =new File(filePath);
                        file.transferTo(dest);
                        dto.setCustomerImg(" " + file.getOriginalFilename());
                    }
            } catch (IOException e){
                log.info("오류 발생");
            }
            customer.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
            customer.setName(dto.getName());
            customer.setNickname(dto.getNickname());
            customer.setTel(dto.getTel());
            customer.setCustomerImg(dto.getCustomerImg());
            customerRepository.save(customer);
        }
    }

    public void delete(Customer currentUser) {customerRepository.delete(currentUser);}

    public String findById(String name, String tel){
     String myId =customerRepository.findId(name,tel);
        if(myId != null){
            return myId;
        }
        return null;
    }

    public Customer findMyPwd(String name, String tel, String id){
        Customer customer = customerRepository.findpw(name,tel,id);
        return customer;
    }

    public String createTempPwd(){
        String[] tempPwd = new String[]{
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z"
        };
        Random random = new Random();
        String pwd = "";
        int i;
        int index = 0;
        for(i = 0; i < 8; i++){
            index = random.nextInt(tempPwd.length);
            pwd += (tempPwd[index]);
        }
        return pwd;
    }

    //생성한 임시 비밀번호 저장
    @Transactional
    public void editTempPwd(String id, String tempPwd){
        Customer customer = customerRepository.findById(id).orElse(null);
        customer.setPassword(bCryptPasswordEncoder.encode(tempPwd));
        customerRepository.save(customer);
    }
}

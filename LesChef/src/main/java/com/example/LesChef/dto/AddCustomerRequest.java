package com.example.LesChef.dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
// 회원 가입 필요한 아이디 받기
public class AddCustomerRequest {
    private String id;
    private String pw;
    private String nickname;
    private String name;
    private String tel;
//    private Blob profileImg;

}

package com.example.LesChef.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

//   메인 페이지
    @GetMapping("/main")
    public String mainPage(){
        return "/html/MainPage";
    }

    @GetMapping("/FindIdPw")
    public String findIdPw(){
        return "/html/FindIdPw";
    }

//   카테고리 페이지
    @GetMapping("/list")
    public String listPage(){
        return "/html/List";
    }


//   상세정보 페이지 이동
    @GetMapping("/inform")
    public String informPage(){
        return "/html/inform";
    }
// 공유 레시피 상세 정보 페이지 이동
    @GetMapping("/informShare")
    public String informSharePage(){
        return "/html/informShare";
    }

// 게시판 메인페이지
    @GetMapping("/notice")
    public String noticePage(){
        return "/html/NoticeBoardMain";
    }
// 게시판 작성 페이지
    @GetMapping("/noticeRegist")
    public String noticeRegistPage(){
        return "/html/NoticeRegist";
    }
//  게시판 세부 내용 페이지 이동
    @GetMapping("/noticeInform")
    public String noticeInfo(){
        return "/html/Gesigeul";
    }
    //  게시판 작성 페이지 이동
    @GetMapping("/noticeWrite")
    public String noticeWrite(){
        return "/html/NoticeRegist";
    }
    //  게시판 수정 페이지 이동
    @GetMapping("/noticeEdit")
    public String noticeEdit(){
        return "/html/GesigeulEdit";
    }

}

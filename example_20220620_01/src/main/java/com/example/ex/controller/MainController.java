package com.example.ex.controller;

import com.example.ex.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor // final 이 붙은 필드를 매개변수로 하는 생성자 생성
public class MainController {
//    MainService mainService = new MainService();
    // 필드주입 (권고하지 않음)
    // @Autowired
    // private MainService mainService;

//    MainService mainService;
//    // 생성자 주입
//    @Autowired
//    MainController(MainService mainService) {
//        this.mainService = mainService;
//    }

    // lombok 활용
    private  final MainService mainService;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/req1")
    public String req1(Model model) {
        String data = mainService.req1();
        model.addAttribute("data", data);
        return "req1";
    }




}

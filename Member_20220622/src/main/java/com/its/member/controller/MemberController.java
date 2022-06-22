package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/save-form")
    public String saveForm() {
        return "memberPages/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "memberPages/login";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null) {
            model.addAttribute("loginMember", loginResult);
            session.setAttribute("loginMemberEmail", loginResult.getMemberEmail());
            session.setAttribute("loginId", loginResult.getId());
            return "memberPages/main";
        } else {
            return "memberPages/login";
        }
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute MemberDTO memberDTO) {
//        boolean loginResult = memberService.login(memberDTO);
//        if (loginResult) {
//            return "memberPages/main";
//        } else {
//            return "memberPages/login";
//        }
//    }

}

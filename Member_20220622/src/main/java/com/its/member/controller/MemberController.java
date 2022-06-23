package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        System.out.println("loginResult = " + loginResult);
        if(loginResult != null) {
            model.addAttribute("loginMember", loginResult);
            session.setAttribute("loginMemberEmail", loginResult.getMemberEmail());
            session.setAttribute("loginId", loginResult.getId());
            return "memberPages/main";
        } else {
            return "memberPages/login";
        }

    }

    // 회원목록 조회
    @GetMapping("/")
        public String findAll(Model model) {
            List<MemberDTO> memberDTOList = memberService.findAll();
            model.addAttribute("memberList", memberDTOList);
            return "memberPages/list";

        }

        // 상세조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/detail";
    }

    @GetMapping("/ajax/{id}")
    public @ResponseBody MemberDTO findByIdAjax(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        return memberDTO;
    }


//    @GetMapping("/{id}")
//        public String delete(@RequestParam("id") Long id) {
//        boolean deleteResult = memberService.delete(id);
//        if (deleteResult) {
//            return "redirect:/findAll";
//        } else {
//            return "delete-fail";
//        }
//    }







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


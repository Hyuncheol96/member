package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 아이디 중복체크
    @PostMapping("/emailCheck")
    public @ResponseBody String dupCheck(@RequestParam String memberEmail) {
        String checkResult = memberService.dupCheck(memberEmail);
        return checkResult;
    }

    @GetMapping("/login-form")
    public String loginForm(@RequestParam(value = "redirectURL", defaultValue = "/") String redirectURL, Model model) {
        model.addAttribute("redirectURL", redirectURL);
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model, HttpSession session,
                        @RequestParam(value = "redirectURL", defaultValue = "/") String redirectURL) {
        MemberDTO loginResult = memberService.login(memberDTO);
        System.out.println("loginResult = " + loginResult);
        if (loginResult != null) {
            session.setAttribute("loginMemberEmail", loginResult.getMemberEmail());
            session.setAttribute("loginId", loginResult.getId());
            System.out.println("redirectURL :" + redirectURL);
            return "redirect:" + redirectURL; // 로그인 하지 않은 사용자가 로그인 직전에 요청한 주소로 보내줌
        } else {
            return "memberPages/login";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 전체룰 무효화
        session.removeAttribute("loginEmail"); // loginEmail만 세션값 삭제
        return "redirect:/";
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

    // ajax 상세조회
    @GetMapping("/ajax/{id}")
    public @ResponseBody MemberDTO findByIdAjax(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        return memberDTO;
    }

    // get 요청 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/";

    }

    /**
     * /member/3 : 조회(get) R, 저장(post) C, 수정(put) U, 삭제(delete) D
     */


    // delete 요청 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAjax(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK); // ajax 호출한 부분에 리턴으로 200 응답을 줌.
    }

    // 회원정보 수정
//    @GetMapping("/update/{id}")
//    public String updateForm(HttpSession session, Model model) {
//        Long updateId = (Long) session.getAttribute("loginId");
//        MemberDTO memberDTO = memberService.findById(updateId);
//        model.addAttribute("updateMember", memberDTO);
//        return "memberPages/update";
//    }

//    @GetMapping("/update-form")
//    public String updateForm(HttpSession session, Model model) {
//        Long updateId = (Long) session.getAttribute("loginId");
//        MemberDTO memberDTO = memberService.findById(updateId);
//        model.addAttribute("updateMember", memberDTO);
//        return "memberPages/update";
//    }


    @GetMapping("/mypage")
    public String mypageForm() {
        return "memberPages/mypage";
    }


    // 수정화면 요청
    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        System.out.println("session = " + session + ", model = " + model);
        Long id = (Long) session.getAttribute("loginId");
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("updateMember", memberDTO);
        return "memberPages/update";
    }

    // 수정처리
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    // 수정처리(put 요청)
    @PutMapping("/{id}")
    public ResponseEntity updateByAjax(@RequestBody MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @GetMapping("/{id}")
//    public String delete(@RequestParam("id") Long id) {
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


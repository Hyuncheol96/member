package com.its.member;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    public MemberDTO newMember() {
        MemberDTO member = new MemberDTO("테스트이메일", "테스트비밀번호", "테스트이름", 10, "테스트폰번호");
            return member;
    }


    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입 테스트")
    public  void memberSaveTest() {
//        MemberDTO memberDTO = new MemberDTO("이메일", "비번", "이름", 10, "번호");
//        Long saveId = memberService.save(memberDTO);
        Long saveId = memberService.save(newMember());
        MemberDTO memberDTO = memberService.findById(saveId);
        assertThat(newMember().getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public  void loginTest() {
        String memberEmail = "로그인용이메일";
        String memberPassword = "로그인용비번";
        String memberName = "로그인용이름";
        int memberAge = 99;
        String memberPhone = "로그인용전화번호";
        MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword, memberName, memberAge, memberPhone);
        Long savedId = memberService.save(memberDTO);
        // 로그인 객체 생성 후 로그인
        MemberDTO loginMemberDTO = new MemberDTO();
        loginMemberDTO.setMemberEmail(memberEmail);
        loginMemberDTO.setMemberPassword(memberPassword);
        MemberDTO loginResult = memberService.login(loginMemberDTO);
        // 로그인 겨로가가 not null 이면 통과
        assertThat(loginResult).isNotNull();


    }







    }


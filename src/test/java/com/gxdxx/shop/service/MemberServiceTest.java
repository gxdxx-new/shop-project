package com.gxdxx.shop.service;

import com.gxdxx.shop.dto.MemberFormDto;
import com.gxdxx.shop.entity.Member;
import com.gxdxx.shop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MemberFormDto createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("경북 경산시 대동");
        memberFormDto.setPassword("123123");
        return memberFormDto;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        MemberFormDto memberFormDto = this.createMember();
        Member member = Member.createMember(memberFormDto.getName(), memberFormDto.getEmail(), memberFormDto.getAddress(), memberFormDto.getPassword(), passwordEncoder);
        Long memberId = memberService.saveMember(member);
        Member savedMember = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        MemberFormDto memberFormDto = this.createMember();
        Member member1 = Member.createMember(memberFormDto.getName(), memberFormDto.getEmail(), memberFormDto.getAddress(), memberFormDto.getPassword(), passwordEncoder);
        Member member2 = Member.createMember(memberFormDto.getName(), memberFormDto.getEmail(), memberFormDto.getAddress(), memberFormDto.getPassword(), passwordEncoder);
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

}
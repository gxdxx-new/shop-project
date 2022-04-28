package com.gxdxx.shop.entity;

import com.gxdxx.shop.constant.Role;
import com.gxdxx.shop.dto.MemberFormDto;
import com.gxdxx.shop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Transactional
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberTest {

    @Spy
    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("회원 엔티티 생성 테스트")
    @Test
    void createMember() {
        //given
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@test.com");
        memberFormDto.setName("test");
        memberFormDto.setAddress("testAddress");
        memberFormDto.setPassword("123123");

        //when
        Member member = Member.createMember(memberFormDto.getName(), memberFormDto.getEmail(), memberFormDto.getAddress(), memberFormDto.getPassword(), passwordEncoder);

        //then
        Assertions.assertThat(member.getRole()).isEqualTo(Role.USER);
    }

//    @Autowired
//    MemberRepository memberRepository;
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Test
//    @DisplayName("Auditing 테스트")
//    @WithMockUser(username = "gxdxx", roles = "USER")
//    public void auditingTest() {
//        Member newMember = new Member();
//        memberRepository.save(newMember);
//
//        em.flush();
//        em.clear();
//
//        Member member = memberRepository.findById(newMember.getId())
//                .orElseThrow(EntityNotFoundException::new);
//
//        System.out.println("register time : " + member.getRegisterTime());
//        System.out.println("update time : " + member.getUpdateTime());
//        System.out.println("create member : " + member.getCreatedBy());
//        System.out.println("modify member : " + member.getModifiedBy());
//    }

}
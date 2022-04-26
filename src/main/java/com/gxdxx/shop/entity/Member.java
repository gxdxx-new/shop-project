package com.gxdxx.shop.entity;

import com.gxdxx.shop.constant.Role;
import com.gxdxx.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@ToString
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.name = memberFormDto.getName();
        member.email = memberFormDto.getEmail();
        member.address = memberFormDto.getAddress();
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.password = password;
        if (memberFormDto.getEmail().equals("admin@admin.com")) {
            member.role = Role.ADMIN;
        } else {
            member.role = Role.USER;
        }
        return member;
    }

}
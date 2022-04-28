package com.gxdxx.shop.entity;

import com.gxdxx.shop.constant.Role;
import com.gxdxx.shop.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static Member createMember(String name, String email, String address, String password, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.name = name;
        member.email = email;
        member.address = address;
        String encodedPassword = passwordEncoder.encode(password);
        member.password = encodedPassword;
        if (email.equals("admin@admin.com")) {
            member.role = Role.ADMIN;
        } else {
            member.role = Role.USER;
        }
        return member;
    }

}
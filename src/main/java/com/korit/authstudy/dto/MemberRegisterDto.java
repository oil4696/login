package com.korit.authstudy.dto;

import com.korit.authstudy.domain.entity.Member;
import com.korit.authstudy.domain.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@RequiredArgsConstructor
public class MemberRegisterDto {

    private String username;
    private String password;
    private String fullName;
    private String email;

    public Member toEntity(BCryptPasswordEncoder passwordEncoder) {
        return Member.builder()
                .memberName(username)
                .password(passwordEncoder.encode(password)) //encode 암호화 해주는 함수
                .name(fullName)
                .email(email)
                .build();
    }
}

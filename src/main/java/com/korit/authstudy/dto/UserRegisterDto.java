package com.korit.authstudy.dto;

import com.korit.authstudy.domain.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@RequiredArgsConstructor
public class UserRegisterDto {

    //ioc 의 부품이 아니라서 BCryptPasswordEncoder 자동으로 바꿀수 없음

    // 암호화는 servise단계에서 이루어짐

//    private BCryptPasswordEncoder passwordEncoder;
//여기서는 암호화 해도 제어가 안돼기 때문에 의미가 없는 코드임

    private String username;
    private String password;
    private String fullName;
    private String email;

    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) //encode 암호화 해주는 함수
                .fullName(fullName)
                .email(email)
                .build();
    }
}


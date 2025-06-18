package com.korit.authstudy.service;

import com.korit.authstudy.domain.entity.User;
import com.korit.authstudy.dto.JwtDto;
import com.korit.authstudy.dto.LoginDto;
import com.korit.authstudy.dto.UserRegisterDto;
import com.korit.authstudy.repository.UsersRepository;
import com.korit.authstudy.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private  final JwtUtil jwtUtil;

    public User register(UserRegisterDto dto) {
        // save가 jpa에서 제공하는 자동으로 쿼리문을 만들어주는 함수
        User insertedUser = usersRepository.save(dto.toEntity(passwordEncoder));
        return insertedUser;
        // save가
    }

    public JwtDto login(LoginDto dto) {
        List<User> foundusers = usersRepository.findByUsername(dto.getUsername());
        if (foundusers.isEmpty()) {
            System.out.println("아이디 없음");
            return null;
        }
        User user = foundusers.get(0);
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            System.out.println("비밀번호 틀림");
            return null;
        }
        System.out.println("로그인 성공 토큰 생성");
        String token = jwtUtil.generateAccessToken(user.getId().toString());
        return JwtDto.builder()
                .accessToken(token)
                .build();
    }


}

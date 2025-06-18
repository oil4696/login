package com.korit.authstudy.service;


import com.korit.authstudy.domain.entity.Member;
import com.korit.authstudy.domain.entity.User;
import com.korit.authstudy.dto.MemberRegisterDto;
import com.korit.authstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRegister;

    public Member register(MemberRegisterDto dto){
        System.out.println(dto);

        Member insertedUser = memberRegister.save(dto.toEntity(passwordEncoder));
        return insertedUser;
    }
}

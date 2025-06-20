package com.korit.authstudy.controller;


import com.korit.authstudy.dto.JwtDto;
import com.korit.authstudy.dto.LoginDto;
import com.korit.authstudy.dto.UserRegisterDto;
import com.korit.authstudy.security.service.JwtService;
import com.korit.authstudy.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor    // 18 final과 세트로 와야함
public class UserController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersService usersService;
    private final JwtService jwtService;

    @PostMapping
    // dto가 생성된 이후에 매개변수로 대입이 된다
    // json 객체가 들어올만 RequestBody
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto dto) {
        log.info("DTO: {}", dto);
        return ResponseEntity.ok(usersService.register((dto)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){
        JwtDto jwtDto = usersService.login(dto);
        System.out.println("로그인 컨트롤러 호출");
        return ResponseEntity.ok(jwtDto);
    }

    @GetMapping("/login/status")
    public ResponseEntity<?> getLoginStatus(@RequestHeader("Authorization") String authorization) {
        System.out.println(authorization);
        return ResponseEntity.ok(jwtService.validLoginAccessToken(authorization));
    }

    @GetMapping("/principal")
    public ResponseEntity<?> getPrincipalUser() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }

}

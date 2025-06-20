package com.korit.authstudy.security.filter;

import com.korit.authstudy.domain.entity.User;
import com.korit.authstudy.repository.UsersRepository;
import com.korit.authstudy.security.jwt.JwtUtil;
import com.korit.authstudy.security.model.PrincipalUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("JWT AccessToken 검사");
        HttpServletRequest request = (HttpServletRequest) servletRequest; // 다운케스팅한거임
        // 해당 메소드가 아니명 그냥 다음 필터로 넘김
        List<String>methods = List.of("POST","GET","PUT", "PATCH", "DELETE");
        if (!methods.contains(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String authorization = request.getHeader("Authorization");
        System.out.println("Bearer 토큰:" + authorization);
        if (jwtUtil.isBearer(authorization)) {
            String accessToken = jwtUtil.removeBearer(authorization);
            // filter에서 controller로 아직 넘어가지 않아 예외를 처리할 수 없다.
            try {
                Claims claims = jwtUtil.getClaims(accessToken);
                String id = claims.getId();
                Integer userId = Integer.parseInt(id);
                Optional<User> foundUserOptiomcal = usersRepository.findById(userId);
                foundUserOptiomcal.ifPresentOrElse((user) -> {
                    // UserEntity를 Security에서 인증 객체로 사용할 PrincipalUser롤 변환
                    PrincipalUser principalUser = PrincipalUser.builder()
                            .userId(user.getId())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .build();
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalUser, "", principalUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("인증 성공");
                    System.out.println(authentication.getName());
                }, () -> {
                        throw new AuthenticationServiceException("인증 실패");
                });

            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

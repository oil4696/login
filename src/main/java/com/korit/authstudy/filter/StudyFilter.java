package com.korit.authstudy.filter;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StudyFilter  implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("전처리");
        filterChain.doFilter(servletRequest, servletResponse);  ///doFilter 다음 필터를 가져옴
        System.out.println("후처리");
    }
}

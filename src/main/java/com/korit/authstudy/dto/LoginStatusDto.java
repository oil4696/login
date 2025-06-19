package com.korit.authstudy.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginStatusDto {
    private String status;
    private boolean isLogin;
    private  Integer userId;
}

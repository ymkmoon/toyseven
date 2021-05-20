package com.toyseven.ymk.admin.dto.response;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    @NotNull
    private String accessToken;
}

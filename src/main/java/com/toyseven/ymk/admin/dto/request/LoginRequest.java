package com.toyseven.ymk.admin.dto.request;

import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class LoginRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
}

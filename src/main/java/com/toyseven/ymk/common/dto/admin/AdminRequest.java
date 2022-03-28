package com.toyseven.ymk.common.dto.admin;

import com.sun.istack.NotNull;

import lombok.Getter;

@Getter
public class AdminRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
}

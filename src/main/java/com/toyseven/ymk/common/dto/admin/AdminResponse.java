package com.toyseven.ymk.common.dto.admin;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponse {

    @NotNull
    private String accessToken;
}

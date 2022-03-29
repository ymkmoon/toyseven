package com.toyseven.ymk.common.dto.admin;

import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminResponse {

    @NotNull
    private String accessToken;
    
    @Builder
    public AdminResponse(String accessToken) {
    	this.accessToken = accessToken;
    }
}

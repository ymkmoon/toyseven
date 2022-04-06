package com.toyseven.ymk.admin;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.TokenDto;

@Transactional
public interface AdminService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
//	public void saveRefreshToken(RefreshTokenDto.Request refreshTokenRequest);
	public void saveRefreshToken(TokenDto.Response token);
	public boolean validateRegistRefreshToken(TokenDto.RefreshRequest refreshRequest);
}

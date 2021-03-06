package com.toyseven.ymk.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.toyseven.ymk.common.dto.TokenDto;

@Transactional
public interface JwtService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	public TokenDto.RefreshResponse saveRefreshToken(TokenDto.Request token);
	public boolean validateRegistRefreshToken(TokenDto.RefreshRequest refreshRequest);
}

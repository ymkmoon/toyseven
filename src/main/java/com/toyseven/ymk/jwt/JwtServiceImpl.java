package com.toyseven.ymk.jwt;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.TokenDto;
import com.toyseven.ymk.common.error.ErrorCode;
import com.toyseven.ymk.common.error.exception.BusinessException;
import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.RefreshTokenEntity;
import com.toyseven.ymk.common.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements UserDetailsService, JwtService {

	private final AdminRepository adminRepository;
	private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AdminEntity adminItem = adminRepository.findAccountByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return User.builder()
                .username(adminItem.getUsername())
                .password(adminItem.getPassword())
                .roles(adminItem.getRole().getName())
                .build();
    }

	@Override
	public String saveRefreshToken(TokenDto.Request token) {
		String username = JwtUtil.getUsernameFromToken(token.getRefreshToken());
		AdminEntity admin = adminRepository.findAccountByUsername(username).orElseThrow(
				() -> new BusinessException("사용자를 찾을 수 없습니다.", ErrorCode.USER_NAME_NOT_FOUND));
		
		boolean exists = refreshTokenRepository.existsByAdminId(admin);
		if(exists) {
			refreshTokenRepository.deleteByAdminId(admin);
		}
		
		RefreshTokenEntity refreshTokenEntity = TokenDto.RefreshRequest.builder()
				.adminId(admin)
				.refreshToken(token.getRefreshToken())
				.build().toEntity();
		return refreshTokenRepository.save(refreshTokenEntity).getRefreshToken();
	}

	@Override
	public boolean validateRegistRefreshToken(TokenDto.RefreshRequest refreshRequest) {
		String refreshToken = refreshRequest.getRefreshToken();
		String usernameInToken = JwtUtil.getUsernameFromToken(refreshToken);
		AdminEntity admin = adminRepository.findAccountByUsername(usernameInToken).orElseThrow(
				() -> new BusinessException("사용자를 찾을 수 없습니다.", ErrorCode.TOKEN_IS_NOT_AUTHORIZED));
		RefreshTokenEntity entity = refreshTokenRepository.findRefreshTokenByAdminId(admin).orElseThrow(
				() -> new BusinessException("등록되지 않은 Refresh Token 입니다.", ErrorCode.TOKEN_IS_NOT_AUTHORIZED));
		return refreshToken.equals(entity.getRefreshToken());
	}
	
	
}


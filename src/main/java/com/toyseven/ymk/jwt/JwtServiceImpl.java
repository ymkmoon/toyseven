package com.toyseven.ymk.jwt;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.Constants;
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
        AdminEntity adminItem = Optional.ofNullable(adminRepository.findAccountByUsername(username))
        		.orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NAME_NOT_FOUND.getDetail()));

        return User.builder()
                .username(adminItem.getUsername())
                .password(adminItem.getPassword())
                .roles(adminItem.getRole().getName())
                .build();
    }

	@Override
	public TokenDto.RefreshResponse saveRefreshToken(TokenDto.Request token) {
		String username = JwtUtil.getUsernameFromToken(token.getRefreshToken(), Constants.REFRESH_TOKEN.getTitle());
		AdminEntity admin = Optional.ofNullable(adminRepository.findAccountByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NAME_NOT_FOUND.getDetail()));
		
		boolean exists = refreshTokenRepository.existsByAdminId(admin);
		if(exists) {
			refreshTokenRepository.deleteByAdminId(admin);
		}
		
		TokenDto.RefreshRequest refreshRequest = TokenDto.RefreshRequest.builder()
				.refreshToken(token.getRefreshToken())
				.build();
		
		RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.save(refreshRequest.toEntity(admin));
		return refreshTokenEntity.toRefreshResponse();
	}

	@Override
	public boolean validateRegistRefreshToken(TokenDto.RefreshRequest refreshRequest) {
		String refreshToken = refreshRequest.getRefreshToken();
		String usernameInToken = JwtUtil.getUsernameFromToken(refreshToken, Constants.REFRESH_TOKEN.getTitle());
		AdminEntity admin = Optional.ofNullable(adminRepository.findAccountByUsername(usernameInToken))
				.orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NAME_NOT_FOUND.getDetail()));
		RefreshTokenEntity entity = Optional.ofNullable(refreshTokenRepository.findRefreshTokenByAdminId(admin))
				.orElseThrow(() -> new BusinessException(ErrorCode.TOKEN_IS_NOT_AUTHORIZED));
		return refreshToken.equals(entity.getRefreshToken());
	}
	
	
}


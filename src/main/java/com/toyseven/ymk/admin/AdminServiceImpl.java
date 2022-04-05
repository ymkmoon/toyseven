package com.toyseven.ymk.admin;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.dto.TokenDto;
import com.toyseven.ymk.common.dto.TokenDto.RefreshRequest;
import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements UserDetailsService, AdminService {

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
	public void saveRefreshToken(TokenDto.Response token) {
		String username = JwtUtil.getUsernameFromToken(token.getRefreshToken());
		AdminEntity admin = adminRepository.findAccountByUsername(username).get();
		
		boolean exists = refreshTokenRepository.existsByAdminId(admin);
		if(exists) {
			refreshTokenRepository.deleteByAdminId(admin);
		} 
		refreshTokenRepository.save(RefreshRequest.builder().adminId(admin)
				.refreshToken(token.getRefreshToken()).build().toEntity());
		
	}
}


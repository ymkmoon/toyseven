package com.toyseven.ymk.admin;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.common.model.entity.AdminEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements UserDetailsService, AdminService {

	private final AdminRepository adminRepository;

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
}


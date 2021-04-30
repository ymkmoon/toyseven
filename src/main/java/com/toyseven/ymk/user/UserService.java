package com.toyseven.ymk.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.model.AdminItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminItem adminItem = userRepository.findAccountByUsername(username);
        if (adminItem == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(adminItem.getUsername())
                .password(adminItem.getPassword())
                .roles(adminItem.getRole().getName())
                .build();
    }
}


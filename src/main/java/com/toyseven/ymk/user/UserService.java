package com.toyseven.ymk.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.model.AdminItem;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

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


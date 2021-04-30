package com.toyseven.ymk.jwtToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.toyseven.ymk.model.AdminItem;
import com.toyseven.ymk.user.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
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
        
//        if ("user_id".equals(username)) {
//            return new User("user_id", "$2a$10$m/enYHaLsCwH2dKMUAtQp.ksGOA6lq7Fd2pnMb4L.yT4GyeAPRPyS",
//                new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
    }
}

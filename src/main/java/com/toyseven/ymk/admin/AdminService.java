package com.toyseven.ymk.admin;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

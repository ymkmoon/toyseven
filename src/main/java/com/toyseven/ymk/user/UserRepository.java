package com.toyseven.ymk.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.model.AdminItem;

@Repository
public interface UserRepository extends JpaRepository<AdminItem, Long> {
	
	AdminItem findAccountByUsername(String username);

}

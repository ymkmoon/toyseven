package com.toyseven.ymk.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.common.model.entity.AdminEntity;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
	
	Optional<AdminEntity> findAccountByUsername(String username);

}

package com.toyseven.ymk.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
	boolean existsByAdminId(AdminEntity admin);
	void deleteByAdminId(AdminEntity admin);
	RefreshTokenEntity findRefreshTokenByAdminId(AdminEntity admin);
}

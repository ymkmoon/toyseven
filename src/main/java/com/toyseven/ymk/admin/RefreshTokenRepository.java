package com.toyseven.ymk.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
	boolean existsByAdminId(AdminEntity admin);
	void deleteByAdminId(AdminEntity admin);
	Optional<RefreshTokenEntity> findRefreshTokenByAdminId(AdminEntity admin);
}

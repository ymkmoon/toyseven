package com.toyseven.ymk.jwt;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.toyseven.ymk.common.dto.TokenDto;
import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.AdminRoleEntity;
import com.toyseven.ymk.common.model.entity.RefreshTokenEntity;

/**
 * @author YMK
 *
 */
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
	
	@Mock AdminRepository adminRepository;
	@Mock RefreshTokenRepository refreshTokenRepository;
	JwtServiceImpl jwtServiceImpl;
	AdminRoleEntity role;
	AdminEntity adminEntity;
	RefreshTokenEntity refreshTokenEntity;
	TokenDto.Response tokenResponse;
	TokenDto.RefreshRequest refreshRequest;
	
	String REFRESH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NTAwOTY3OTcsImlhdCI6MTY0OTgzNzU5NywidXNlcm5hbWUiOiJndWtlIn0.cawbrQRjhyfxb1_kc4_Z6yo7nTkYn1VOxc6rBIjKs867YyYFpyRLoCuFVKZR_aOA4AXUvArOrlvXMiGlJaY8Jg";
	
	@BeforeEach
	void setup() {
		this.jwtServiceImpl = new JwtServiceImpl(adminRepository, refreshTokenRepository);
		this.role = AdminRoleEntity.builder()
				.name("ADMIN")
				.displayName("관리자")
				.build();
		this.adminEntity = AdminEntity.builder()
				.userName("guke")
				.nickname("manager guke")
				.password("guke")
				.role(role)
				.build();
		this.tokenResponse = TokenDto.Response.builder()
				.refreshToken(REFRESH_TOKEN)
				.build();
		this.refreshRequest = TokenDto.RefreshRequest.builder()
				.refreshToken(REFRESH_TOKEN)
				.build();
		this.refreshTokenEntity = RefreshTokenEntity.builder()
				.adminId(adminEntity)
				.refreshToken(REFRESH_TOKEN)
				.build();
	}

	/**
	 * DB에서 유저 조회를 성공 했을 경우
	 */
	@Test
	@DisplayName("유저 조회 성공 테스트 ")
	void Should_Build_User_When_User_Is_Exist() throws Exception {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Method loadUserByUsername = jwtServiceImpl.getClass().getDeclaredMethod("loadUserByUsername", String.class);
		loadUserByUsername.invoke(jwtServiceImpl, "guke");
	}
	
	/**
	 * DB에서 유저 조회를 실패 했을 경우
	 */
	@Test
	@DisplayName("유저 조회 실패 테스트 ")
	void Should_Thorws_Exception_When_User_Is_Not_Exist() throws Exception {
		Method loadUserByUsername = jwtServiceImpl.getClass().getDeclaredMethod("loadUserByUsername", String.class);
		assertThatThrownBy(() -> loadUserByUsername.invoke(jwtServiceImpl, "guke"))
			.isInstanceOf(Exception.class);
	}
	
	/**
	 * Refresh Token 저장에 성공 했을 경우
	 */
	@Test
	@DisplayName("Refresh 토큰 저장 성공 테스트 ")
	void Should_Save_Refresh_Token_When_User_Is_Exist() throws Exception {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Method saveRefreshToken = jwtServiceImpl.getClass().getDeclaredMethod("saveRefreshToken", TokenDto.Response.class);
		saveRefreshToken.invoke(jwtServiceImpl, tokenResponse);
	}
	
	/**
	 * Refresh Token 을 삭제 후 저장에 대한 테스트 케이스
	 */
	@Test
	@DisplayName("기존 Refresh 토큰 삭제 후 저장 성공 테스트 ")
	void Should_Save_Refresh_Token_When_Refresh_Token_Already_Regist() throws Exception {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Mockito.when(refreshTokenRepository.existsByAdminId(adminEntity)).thenReturn(true);
		Method saveRefreshToken = jwtServiceImpl.getClass().getDeclaredMethod("saveRefreshToken", TokenDto.Response.class);
		saveRefreshToken.invoke(jwtServiceImpl, tokenResponse);
	}
	
	/**
	 * 요청받은 Token 에서 Username 을 취득하지 못했을 경우 
	 */
	@Test
	@DisplayName("유저 조회 실패 테스트 ")
	void Should_Thorws_Exception_When_Can_Not_Found_Username_In_Token() throws Exception {
		this.tokenResponse = TokenDto.Response.builder().refreshToken("token").build();
		Mockito.lenient().when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Method saveRefreshToken = jwtServiceImpl.getClass().getDeclaredMethod("saveRefreshToken", TokenDto.Response.class);
		assertThatThrownBy(() -> saveRefreshToken.invoke(jwtServiceImpl, tokenResponse))
			.isInstanceOf(Exception.class);
	}
	
	
	/**
	 * 토큰 갱신에 성공 했을 경우
	 */
	@Test
	@DisplayName("토큰 갱신 성공 테스트 ")
	void Should_Refresh_Access_Token_When_Refresh_Token_Is_Regist() throws Exception {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Mockito.when(refreshTokenRepository.findRefreshTokenByAdminId(adminEntity)).thenReturn(Optional.of(refreshTokenEntity));
		Method validateRegistRefreshToken = jwtServiceImpl.getClass().getDeclaredMethod("validateRegistRefreshToken", TokenDto.RefreshRequest.class);
		validateRegistRefreshToken.invoke(jwtServiceImpl, refreshRequest);
	}
	
	
	/**
	 * 토큰 갱신에 실패 했을 경우
	 */
	@Test
	@DisplayName("토큰 갱신 실패 테스트 ")
	void Should_Throws_Exception_When_Refresh_Token_Is_Not_Regist() throws Exception {
		this.refreshRequest = TokenDto.RefreshRequest.builder().refreshToken("token").build();
		Mockito.lenient().when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Mockito.lenient().when(refreshTokenRepository.findRefreshTokenByAdminId(adminEntity)).thenReturn(Optional.of(refreshTokenEntity));
		Method validateRegistRefreshToken = jwtServiceImpl.getClass().getDeclaredMethod("validateRegistRefreshToken", TokenDto.RefreshRequest.class);
		assertThatThrownBy(() -> validateRegistRefreshToken.invoke(jwtServiceImpl, refreshRequest))
			.isInstanceOf(Exception.class);
	}
}

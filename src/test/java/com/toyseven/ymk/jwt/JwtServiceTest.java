package com.toyseven.ymk.jwt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.toyseven.ymk.common.dto.AdminDto;
import com.toyseven.ymk.common.dto.TokenDto;
import com.toyseven.ymk.common.model.entity.AdminEntity;
import com.toyseven.ymk.common.model.entity.AdminRoleEntity;
import com.toyseven.ymk.common.model.entity.RefreshTokenEntity;

import io.jsonwebtoken.MalformedJwtException;

/**
 * @author YMK
 *
 */
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
	
	@Mock AdminRepository adminRepository;
	@Mock RefreshTokenRepository refreshTokenRepository;
	@InjectMocks JwtServiceImpl jwtServiceImpl;
	AdminRoleEntity role;
	AdminEntity adminEntity;
	RefreshTokenEntity refreshTokenEntity;
	TokenDto.Request tokenRequest;
	TokenDto.RefreshRequest refreshRequest;
	
	static String REFRESH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NTAyNTgwNzQsImlhdCI6MTY0OTk5ODg3NCwidXNlcm5hbWUiOiJndWtlIn0.nM0qvK5DNyV0FNKbvKlz5QNYAbPoHKoWb6ahRJsOknjL4rjj56n1nPSDaminNLiC0f7QZQNN4iQvOaBoqROgvA";
	
	@BeforeEach
	void setup() {
		this.role = AdminRoleEntity.builder().name("ADMIN").displayName("관리자").build();
		this.adminEntity = AdminEntity.builder().userName("guke").nickname("manager guke").password("guke").role(role).build();
		this.tokenRequest = TokenDto.Request.builder().refreshToken(REFRESH_TOKEN).build();
		this.refreshRequest = TokenDto.RefreshRequest.builder().refreshToken(REFRESH_TOKEN).build();
		this.refreshTokenEntity = RefreshTokenEntity.builder().adminId(adminEntity).refreshToken(REFRESH_TOKEN).build();
	}

	/**
	 * DB에서 유저 조회를 성공 했을 경우
	 */
	@Test
	@DisplayName("유저 조회 성공 테스트 ")
	void Should_Build_User_When_User_Is_Exist() throws Exception {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		AdminDto.Request adminRequest = AdminDto.Request.builder().username("guke").password("").build();
		UserDetails userDetails = jwtServiceImpl.loadUserByUsername(adminRequest.getUsername());
		assertThat(userDetails.getUsername(), is(adminRequest.getUsername()));
	}
	
	/**
	 * DB에서 유저 조회를 실패 했을 경우
	 */
	@Test
	@DisplayName("유저 조회 실패 테스트 ")
	void Should_Thorws_Exception_When_User_Is_Not_Exist() throws Exception {
		AdminDto.Request adminRequest = AdminDto.Request.builder().username("guke").password("").build();
		assertThrows(UsernameNotFoundException.class, () -> jwtServiceImpl.loadUserByUsername(adminRequest.getUsername()));
	}
	
	/**
	 * Refresh Token 저장에 성공 했을 경우
	 */
	@Test
	@DisplayName("Refresh 토큰 저장 성공 테스트 ")
	void Should_Save_Refresh_Token_When_User_Is_Exist() throws Exception {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Mockito.when(refreshTokenRepository.existsByAdminId(adminEntity)).thenReturn(false);
		Mockito.when(refreshTokenRepository.save(any())).thenReturn(refreshTokenEntity);
		String refreshToken = jwtServiceImpl.saveRefreshToken(tokenRequest);
		assertThat(refreshToken, is(tokenRequest.getRefreshToken()));
	}
	
	/**
	 * Refresh Token 을 삭제 후 저장에 대한 테스트 케이스
	 */
	@Test
	@DisplayName("기존 Refresh 토큰 삭제 후 저장 성공 테스트 ")
	void Should_Save_Refresh_Token_When_Refresh_Token_Already_Regist() throws Exception {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Mockito.when(refreshTokenRepository.existsByAdminId(adminEntity)).thenReturn(true);
		Mockito.when(refreshTokenRepository.save(any())).thenReturn(refreshTokenEntity);
		String refreshToken = jwtServiceImpl.saveRefreshToken(tokenRequest);
		assertThat(refreshToken, is(tokenRequest.getRefreshToken()));
	}
	
	/**
	 * Token 형식이 잘못 된 경우 
	 */
	@Test
	@DisplayName("유저 조회 실패 테스트 ")
	void Should_Thorws_Exception_When_Can_Not_Found_Username_In_Token() throws Exception {
		this.tokenRequest = TokenDto.Request.builder().refreshToken("token").build();
		assertThrows(MalformedJwtException.class, () -> jwtServiceImpl.saveRefreshToken(tokenRequest));
	}
	
	/**
	 * 토큰 갱신에 성공 했을 경우
	 */
	@Test
	@DisplayName("토큰 갱신 성공 테스트 ")
	void Should_Refresh_Access_Token_When_Refresh_Token_Is_Regist() throws Exception {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Mockito.when(refreshTokenRepository.findRefreshTokenByAdminId(adminEntity)).thenReturn(Optional.of(refreshTokenEntity));
		boolean result = jwtServiceImpl.validateRegistRefreshToken(refreshRequest);
		assertThat(result, is(true));
	}
	
	/**
	 * Token 형식이 잘못 된 경우 
	 */
	@Test
	@DisplayName("토큰 갱신 실패 테스트 ")
	void Should_Throws_Exception_When_Refresh_Token_Is_Not_Regist() throws Exception {
		this.refreshRequest = TokenDto.RefreshRequest.builder().refreshToken("token").build();
		Mockito.lenient().when(adminRepository.findAccountByUsername("guke")).thenReturn(Optional.of(adminEntity));
		Mockito.lenient().when(refreshTokenRepository.findRefreshTokenByAdminId(adminEntity)).thenReturn(Optional.of(refreshTokenEntity));
		assertThrows(MalformedJwtException.class, () -> jwtServiceImpl.validateRegistRefreshToken(refreshRequest));
	}
}

package com.toyseven.ymk.jwt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

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
	
	// Token 데이터에는 만료기간에 대한 설정값이 들어가 있어, unit 시에도 갱신시켜줘야 한다.
	static String REFRESH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NTU0Mzk0NjcsImlhdCI6MTY1NTE4MDI2NywidXNlcm5hbWUiOiJndWtlIn0.6RAUUgOxxxHWCCM7HdnyuqsMSTY1HLFnKPh23574lXSEk0lS_Z8dKiV4P8ScHmHzk_awvbp5VMZljl_EbepZsw";
	
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
	void Should_Build_User_When_User_Is_Exist() {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(adminEntity);
		AdminDto.Request adminRequest = AdminDto.Request.builder().username("guke").password("").build();
		UserDetails userDetails = jwtServiceImpl.loadUserByUsername(adminRequest.getUsername());
		assertThat(userDetails.getUsername(), is(adminRequest.getUsername()));
	}
	
	/**
	 * DB에서 유저 조회를 실패 했을 경우
	 */
	@Test
	@DisplayName("유저 조회 실패 테스트 ")
	void Should_Thorws_Exception_When_User_Is_Not_Exist() {
		AdminDto.Request adminRequest = AdminDto.Request.builder().username("guke").password("").build();
		assertThrows(UsernameNotFoundException.class, () -> jwtServiceImpl.loadUserByUsername(adminRequest.getUsername()));
	}
	
	/**
	 * Refresh Token 저장에 성공 했을 경우
	 */
	@Test
	@DisplayName("Refresh 토큰 저장 성공 테스트 ")
	void Should_Save_Refresh_Token_When_User_Is_Exist() {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(adminEntity);
		Mockito.when(refreshTokenRepository.existsByAdminId(adminEntity)).thenReturn(false);
		Mockito.when(refreshTokenRepository.save(any())).thenReturn(refreshTokenEntity);
		TokenDto.RefreshResponse refreshToken = jwtServiceImpl.saveRefreshToken(tokenRequest);
		assertThat(refreshToken.getRefreshToken(), is(tokenRequest.getRefreshToken()));
	}
	
	/**
	 * Refresh Token 을 삭제 후 저장에 대한 테스트 케이스
	 */
	@Test
	@DisplayName("기존 Refresh 토큰 삭제 후 저장 성공 테스트 ")
	void Should_Save_Refresh_Token_When_Refresh_Token_Already_Regist() {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(adminEntity);
		Mockito.when(refreshTokenRepository.existsByAdminId(adminEntity)).thenReturn(true);
		Mockito.when(refreshTokenRepository.save(any())).thenReturn(refreshTokenEntity);
		TokenDto.RefreshResponse refreshToken = jwtServiceImpl.saveRefreshToken(tokenRequest);
		assertThat(refreshToken.getRefreshToken(), is(tokenRequest.getRefreshToken()));
	}
	
	/**
	 * Token 형식이 잘못 된 경우 
	 */
	@Test
	@DisplayName("유저 조회 실패 테스트 ")
	void Should_Thorws_Exception_When_Can_Not_Found_Username_In_Token() {
		this.tokenRequest = TokenDto.Request.builder().refreshToken("token").build();
		assertThrows(MalformedJwtException.class, () -> jwtServiceImpl.saveRefreshToken(tokenRequest));
	}
	
	/**
	 * 토큰 갱신에 성공 했을 경우
	 */
	@Test
	@DisplayName("토큰 갱신 성공 테스트 ")
	void Should_Refresh_Access_Token_When_Refresh_Token_Is_Regist() {
		Mockito.when(adminRepository.findAccountByUsername("guke")).thenReturn(adminEntity);
		Mockito.when(refreshTokenRepository.findRefreshTokenByAdminId(adminEntity)).thenReturn(refreshTokenEntity);
		boolean result = jwtServiceImpl.validateRegistRefreshToken(refreshRequest);
		assertThat(result, is(true));
	}
	
	/**
	 * Token 형식이 잘못 된 경우 
	 */
	@Test
	@DisplayName("토큰 갱신 실패 테스트 ")
	void Should_Throws_Exception_When_Refresh_Token_Is_Not_Regist() {
		this.refreshRequest = TokenDto.RefreshRequest.builder().refreshToken("token").build();
		Mockito.lenient().when(adminRepository.findAccountByUsername("guke")).thenReturn(adminEntity);
		Mockito.lenient().when(refreshTokenRepository.findRefreshTokenByAdminId(adminEntity)).thenReturn(refreshTokenEntity);
		assertThrows(MalformedJwtException.class, () -> jwtServiceImpl.validateRegistRefreshToken(refreshRequest));
	}
}

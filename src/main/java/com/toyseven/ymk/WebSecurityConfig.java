package com.toyseven.ymk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.toyseven.ymk.oauth.CustomOAuth2UserService;

@EnableWebSecurity
@Configuration
@PropertySource(value = "classpath:application.yml")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private CustomOAuth2UserService customOAuth2UserService;
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

//		configuration.addAllowedOrigin("http://192.168.150.132:8080");
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
//		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/plugins/**", "/websocket/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CORS 설정
		http.authorizeRequests()
				// 페이지 권한 설정
//				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**").hasRole("USER")
				.antMatchers("/loginForm").permitAll()
				.antMatchers("/loginFail").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated().and()
				.cors().and()
				// 로그인 설정
				.formLogin()
				.loginPage("/loginForm")
				.loginProcessingUrl("/login")
				.failureUrl("/loginFail")
				.permitAll()
				.and()
				// 로그아웃 설정
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/loginForm")
				.invalidateHttpSession(true)
				.and()
				// 403 예외처리 핸들링
				.exceptionHandling().accessDeniedPage("/user/denied")
				// oauth2
				.and()
				.oauth2Login()
				.defaultSuccessUrl("/swagger-ui.html")
				.userInfoEndpoint()
				.userService(customOAuth2UserService);
		

//		http.csrf().disable()
		http.csrf()
	      	.csrfTokenRepository(new CookieCsrfTokenRepository());
		http
	        .httpBasic().disable()
	        .cors().configurationSource(corsConfigurationSource());
		http.headers().frameOptions().disable();
	}
}

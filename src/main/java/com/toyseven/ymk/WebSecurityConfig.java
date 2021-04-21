package com.toyseven.ymk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

import com.toyseven.ymk.handler.AuthFailureHandler;
import com.toyseven.ymk.handler.AuthSuccessHandler;
import com.toyseven.ymk.oauth.CustomOAuth2UserService;
import com.toyseven.ymk.user.UserService;

@EnableWebSecurity
@Configuration
@PropertySource(value = "classpath:application.yml")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private CustomOAuth2UserService customOAuth2UserService;

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
				.antMatchers(
						"/h2-console/**", 
						"/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**")
						.permitAll()
				.antMatchers("/loginForm").permitAll()
				.antMatchers("/loginFail").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated().and()
				.cors().and()
				// 로그인 설정
				.formLogin()
	//				.loginPage("/loginForm")
	//				.loginProcessingUrl("/login")
					.permitAll()
					.successHandler(new AuthSuccessHandler())
					.failureHandler(new AuthFailureHandler())
					.and()
				// 로그아웃 설정
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login")
					.invalidateHttpSession(true)
					.and()
				// 403 예외처리 핸들링
				.exceptionHandling().accessDeniedPage("/user/denied")
					.and()
				// oauth2
				.oauth2Login()
					.defaultSuccessUrl("/swagger-ui.html")
					.successHandler(new AuthSuccessHandler())
					.userInfoEndpoint()
					.userService(customOAuth2UserService);
		

//		http.csrf().disable()
		http.csrf()
			.ignoringAntMatchers("/h2-console/**") //2. csrf 설정으로 h2-console 콘솔에서 접속 시도하면 인증화면으로 변경되는 문제 해결
	      	.csrfTokenRepository(new CookieCsrfTokenRepository());
		http
	        .httpBasic().disable()
	        .cors().configurationSource(corsConfigurationSource());
//		http.headers().frameOptions().disable();
		http.headers().frameOptions().sameOrigin(); //3. h2-console 콘솔 접속 후 화면 표시 이상 해결 
	}
	
	@Autowired
	private UserService userService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
}

package com.toyseven.ymk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.toyseven.ymk.handler.AuthFailureHandler;
import com.toyseven.ymk.handler.AuthSuccessHandler;
import com.toyseven.ymk.jwtToken.JwtAuthenticationEntryPoint;
import com.toyseven.ymk.jwtToken.JwtRequestFilter;
import com.toyseven.ymk.jwtToken.JwtUserDetailsService;
import com.toyseven.ymk.oauth.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@PropertySource(value = "classpath:application.yml")
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final CustomOAuth2UserService customOAuth2UserService;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtUserDetailsService jwtUserDetailsService;
	private final JwtRequestFilter jwtRequestFilter;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
//
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
//				.antMatchers("/h2-console/**").hasRole("USER")
				.antMatchers("/authenticate", "/h2-console/**").permitAll()
//				.antMatchers("/h2-console/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.antMatchers(
//						"/h2-console/**", 
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
//				.logout()
//					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//					.logoutSuccessUrl("/login")
//					.invalidateHttpSession(true)
//					.and()
				// oauth2
				.oauth2Login()
					.defaultSuccessUrl("/swagger-ui.html")
					.successHandler(new AuthSuccessHandler())
					.userInfoEndpoint()
					.userService(customOAuth2UserService);
		

		http.csrf().disable();
//		http.csrf()
//			.ignoringAntMatchers("/h2-console/**") //2. csrf 설정으로 h2-console 콘솔에서 접속 시도하면 인증화면으로 변경되는 문제 해결
//	      	.csrfTokenRepository(new CookieCsrfTokenRepository());
		
		// 시큐리티 login 페이지를 보여주고 싶으면 아래 핸들링을 주석처리 
		http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
	        .httpBasic().disable()
//	        .cors().configurationSource(corsConfigurationSource());
			.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()); // For CORS error

//		http.headers().frameOptions().disable();
		//3. h2-console 콘솔 접속 후 화면 표시 이상 해결 
		http.headers().frameOptions().sameOrigin();
		
		// Add a filter to validate the tokens with every request
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
//	@Autowired
//	private UserService userService;
//	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
}

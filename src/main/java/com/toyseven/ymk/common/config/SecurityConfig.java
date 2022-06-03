package com.toyseven.ymk.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.toyseven.ymk.common.error.exception.JwtAccessDeniedHandler;
import com.toyseven.ymk.common.error.exception.JwtAuthenticationEntryPoint;
import com.toyseven.ymk.jwt.JwtRequestFilter;
import com.toyseven.ymk.jwt.JwtServiceImpl;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
	private String jwkSetUri;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtDecoder jwtDecoder(){
		return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
	}
	
	@Configuration
	@Order(1)
	@PropertySource(value = "classpath:application.yml")
	@RequiredArgsConstructor
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
		private final JwtRequestFilter jwtRequestFilter;
		private final JwtServiceImpl jwtService;
	    
	    @Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/voc/answer")
				.authorizeRequests()
					.anyRequest().hasAnyRole("ADMIN", "ADMIN2")
				.and().cors();
			http.csrf().disable(); 
	    	http.headers()
	    		.frameOptions().sameOrigin(); 
	    	http.exceptionHandling()
	    		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
	    		.accessDeniedHandler(jwtAccessDeniedHandler)
	    		.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 토큰 기반 인증이므로 세션 사용 x
	    	http.httpBasic().disable()
				.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
	    	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		}
	    
		
		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
		}
		
		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

	}
	
	@Configuration
	@Order(2)
	@PropertySource(value = "classpath:application.yml")
	@RequiredArgsConstructor
	public class OAuthSecurityConfig extends WebSecurityConfigurerAdapter {
		
		private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
            	.authorizeRequests()
	            .antMatchers("/**/**")
	            	.permitAll()
                .anyRequest().authenticated()
                .and()
                .cors() // cross-origin
                .and()
				.oauth2ResourceServer()
					.authenticationEntryPoint(jwtAuthenticationEntryPoint)
					.accessDeniedHandler(jwtAccessDeniedHandler)
					.jwt()
					.jwkSetUri(jwkSetUri);
			
			http.csrf().disable(); 
			http.headers()
				.frameOptions().sameOrigin(); 
			http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 토큰 기반 인증이므로 세션 사용 x
			http.httpBasic().disable()
				.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
		}
		
		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
}

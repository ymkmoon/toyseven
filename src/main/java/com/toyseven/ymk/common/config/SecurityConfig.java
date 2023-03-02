package com.toyseven.ymk.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.toyseven.ymk.common.ResponseEntityComponent;
import com.toyseven.ymk.common.error.exception.JwtAccessDeniedHandler;
import com.toyseven.ymk.common.error.exception.JwtAuthenticationEntryPoint;
import com.toyseven.ymk.common.filter.DefaultRequestFilter;
import com.toyseven.ymk.common.filter.JwtRequestFilter;
import com.toyseven.ymk.common.filter.OAuth2RequestFilter;
import com.toyseven.ymk.jwt.JwtService;

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
	public class AdminSecurityConfig {
		
		private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

		private final JwtService jwtService;
	    
		@Bean
		protected SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {

			http
				.requestMatchers().antMatchers("/voc/answer").and()
				.requestMatchers().antMatchers("/actuator/**").and()
				.authorizeRequests()
//				.anyRequest().hasAnyRole("ADMIN", "SYSTEM")
				.antMatchers(HttpMethod.POST, "/voc/answer").hasAnyRole("ADMIN", "ADMIN2")
				.antMatchers("/actuator/**").hasAnyRole("ADMIN", "SYSTEM")
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
	    	http.addFilterBefore(new JwtRequestFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
	    	
	    	return http.build();
		}
	    
	    @Bean
		public AuthenticationManager authenticationManager(
				AuthenticationConfiguration authenticationConfiguration) throws Exception {
			return authenticationConfiguration.getAuthenticationManager();
		}
		

	}
	
	@Configuration
	@Order(2)
	@PropertySource(value = "classpath:application.yml")
	@RequiredArgsConstructor
	public class UserSecurityConfig {
		
		private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
		private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
		
		private final ResponseEntityComponent responseEntityComponent;
		@Value("${aws.cognito.domaim}")
		private String ISSUER_URI;
		
		@Bean
		protected SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
			
			http
				.requestMatchers().antMatchers("/voc/question").and()
				.requestMatchers().antMatchers("/cognito/payload/**").and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/voc/question").authenticated()
	        	.antMatchers(HttpMethod.PATCH, "/voc/question").authenticated()
	        	.antMatchers(HttpMethod.GET, "/cognito/payload/**").authenticated()
				.and().cors()
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
			http.addFilterBefore(new OAuth2RequestFilter(responseEntityComponent, ISSUER_URI), UsernamePasswordAuthenticationFilter.class);
			
			return http.build();
		}
		
		@Bean
		public AuthenticationManager authenticationManager(
				AuthenticationConfiguration authenticationConfiguration) throws Exception {
			return authenticationConfiguration.getAuthenticationManager();
		}
	}
	
	@Configuration
	@Order(3)
	@PropertySource(value = "classpath:application.yml")
	@RequiredArgsConstructor
	public class DefaultSecurityConfig {
		
		@Bean
		protected SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
			http
            	.authorizeRequests()
	            .antMatchers("/**/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .cors() // cross-origin
                .and();
			
			http.csrf().disable(); 
			http.headers()
				.frameOptions().sameOrigin(); 
			http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 토큰 기반 인증이므로 세션 사용 x
			http.httpBasic().disable()
				.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
//			http.addFilter(defaultRequestFilter);
			http.addFilterBefore(new DefaultRequestFilter(), UsernamePasswordAuthenticationFilter.class);
			
			return http.build();
		}
		
		@Bean
		public AuthenticationManager authenticationManager(
				AuthenticationConfiguration authenticationConfiguration) throws Exception {
			return authenticationConfiguration.getAuthenticationManager();
		}
	}
}

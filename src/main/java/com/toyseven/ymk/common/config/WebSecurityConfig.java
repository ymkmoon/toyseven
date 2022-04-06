package com.toyseven.ymk.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.toyseven.ymk.common.error.Exception.JwtAccessDeniedHandler;
import com.toyseven.ymk.common.error.Exception.JwtAuthenticationEntryPoint;
import com.toyseven.ymk.jwt.JwtServiceImpl;
import com.toyseven.ymk.jwt.JwtRequestFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@PropertySource(value = "classpath:application.yml")
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtRequestFilter jwtRequestFilter;
	private final JwtServiceImpl jwtService;
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(
            		"/voc/answer"
//            		,
//            		"/auth/refresh"
            		).hasRole("ADMIN")
	            .antMatchers("/**/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .cors().and();
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
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
	}
}

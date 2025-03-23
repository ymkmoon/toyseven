package com.toyseven.ymk.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.toyseven.ymk.common.ResponseEntityComponent;
import com.toyseven.ymk.common.error.exception.JwtAccessDeniedHandler;
import com.toyseven.ymk.common.error.exception.JwtAuthenticationEntryPoint;
import com.toyseven.ymk.common.filter.DefaultRequestFilter;
import com.toyseven.ymk.common.filter.JwtRequestFilter;
import com.toyseven.ymk.common.filter.OAuth2RequestFilter;
import com.toyseven.ymk.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@PropertySource(value = "classpath:application.yml")
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtService jwtService;
    private final ResponseEntityComponent responseEntityComponent;
    private final UserDetailsService userDetailsService;

    @Value("${aws.cognito.domaim}")
    private String ISSUER_URI;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
    
    @Value("${spring.security.debug:false}")
    boolean securityDebug;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
            return (web) -> web.debug(securityDebug)
            		.ignoring()
            		.requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                    .requestMatchers(new AntPathRequestMatcher( "/favicon.ico"))
                    .requestMatchers(new AntPathRequestMatcher( "/css/**"))
                    .requestMatchers(new AntPathRequestMatcher( "/js/**"))
                    .requestMatchers(new AntPathRequestMatcher( "/img/**"))
                    .requestMatchers(new AntPathRequestMatcher( "/lib/**"));
    }

    // ✅ 명확하게 AuthenticationManager를 설정하여 Spring이 감지할 수 있도록 등록
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }

    private HttpSecurity applyCommonSecurityConfig(HttpSecurity http) throws Exception {
    	http.httpBasic().disable();
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
    	AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authenticationManager = sharedObject.build();
        
        applyCommonSecurityConfig(http)
        	.exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(new AntPathRequestMatcher("/voc/answer")).hasAnyRole("ADMIN", "ADMIN2")
                    .anyRequest().permitAll()
            )
            .authenticationManager(authenticationManager)
            .addFilterBefore(new JwtRequestFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
    	
    	AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authenticationManager = sharedObject.build();

        applyCommonSecurityConfig(http)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/voc/question")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/voc/question")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/cognito/payload/**")).authenticated()
                .anyRequest().permitAll()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .jwt(jwt -> jwt.jwkSetUri(jwkSetUri))
            )
            .authenticationManager(authenticationManager)
            .addFilterBefore(new OAuth2RequestFilter(responseEntityComponent, ISSUER_URI), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
//    	AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
//        AuthenticationManager authenticationManager = sharedObject.build();
        		
        applyCommonSecurityConfig(http)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(new AntPathRequestMatcher("/**/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/stations/**")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/stations/*")).permitAll()
                .anyRequest().permitAll()
            )
//            .authenticationManager(authenticationManager)
            .addFilterBefore(new DefaultRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}


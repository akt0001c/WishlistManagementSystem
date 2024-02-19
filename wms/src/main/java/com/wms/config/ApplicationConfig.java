package com.wms.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class ApplicationConfig {
	
private MyUserDetailsService muservice;


	

	
public ApplicationConfig(MyUserDetailsService muservice) {
	super();
	this.muservice = muservice;
}

@Bean
public PasswordEncoder  getPasswordEncoder() {
	return new BCryptPasswordEncoder();
}

@Bean
public SecurityFilterChain mySercurityFilterchainHandler(HttpSecurity http) throws Exception{
 
	CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
	http
	.sessionManagement(sessionManagement-> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	
	.cors(cors->{
		cors.configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				
				return cfg;
			}
			
		});
	})
	 
	 .authorizeHttpRequests(auth->{
		 
		 auth.requestMatchers(HttpMethod.POST,"/api/signUp").permitAll()
		     .anyRequest().authenticated();
	 })
	 
		.csrf(csrf->csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("users/signUp").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		 .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		  .addFilterAfter(new CsrfCookieFilter(),BasicAuthenticationFilter.class)
		  .addFilterAfter(new JwtTokenGeneratorFilter(),CsrfCookieFilter.class )
		  
	 
	 .formLogin(Customizer.withDefaults())
	 .httpBasic(Customizer.withDefaults());
	 
	
	return http.build();
}


@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
}

}

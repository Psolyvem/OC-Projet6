package com.paymybuddy.server.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig
{
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	private String jwtKey = "mYvXUlPGJXMd2gOJQfPomy8FFm8mNf4e/sBC8r4+XMp5gQk7ljcXJ7DIXzLzX4gthaCw3J5ALw+XIWE6NHnbGWmWQzvVkQm6ytCwNL8m44p+WgmTGkKi0sVcpDTc0u2uOf9Arez1mg7XUJOS6tuuMQa3uMR+eNEjaD28V0z3iNkR7ZFy2E78v5kCaO9zQLc0jRfKdiQE+b4XCUMZLyU14E2k6Vmdi1eHGjKMqA67US7oSvYWYy2FVP3FzHnvbW2MuLka3dFf5v4/bCP4vmoArtIIlYeVWq8AHgLgiJgZcISuL2iELJF+jiMGUGgqK7E5EyoeoTzru/hPoIThWjcdxEQYxAeZ7k+uQUYFFrHviuo=";


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{

		return http.authorizeHttpRequests(auth ->
		{
//			auth.requestMatchers("/admin").hasRole("ADMIN");
//			auth.requestMatchers("/user").hasRole("USER");
			auth.requestMatchers("/login").permitAll();
			auth.requestMatchers("/*").authenticated();
			auth.anyRequest().authenticated();
		})
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
				.httpBasic(Customizer.withDefaults())
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception
	{
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public CorsFilter corsFilter()
	{
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*"); // Autorise toutes les origines
		config.addAllowedMethod("*"); // Autorise toutes les méthodes (GET, POST, etc.)
		config.addAllowedHeader("*"); // Autorise tous les en-têtes
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length,"RSA");
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	}
}

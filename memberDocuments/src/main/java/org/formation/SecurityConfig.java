package org.formation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		    .authorizeHttpRequests(
				(authz) -> authz.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/members/**").authenticated()
						.requestMatchers("/api/members/**").hasRole("ADMIN").anyRequest().authenticated())
				.formLogin(Customizer.withDefaults())
				.oauth2Login(Customizer.withDefaults())
				;
		return http.build();
	}

//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.withUsername("user").password("{noop}secret").roles("USER").build();
//		UserDetails admin = User.withUsername("admin").password("{noop}secret").roles("ADMIN").build();
//
//		return new InMemoryUserDetailsManager(admin, user);
//	}
	
	@Bean
	PasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

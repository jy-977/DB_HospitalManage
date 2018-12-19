package com.example.DB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import example.CustomUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring()
			.antMatchers("/assets/**","/images/**");
	}
	
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		
		http.authorizeRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/**").permitAll();
		
	}
	*/
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user=
				User.withDefaultPasswordEncoder()
					.username("kdhong")
					.password("kdhong")
					.roles("ADMIN")
					.build();
		
		return new InMemoryUserDetailsManager(user);
	}
	
	
}

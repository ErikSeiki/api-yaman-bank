package br.com.yaman.bank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter{
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http
	        .authorizeRequests()
	        	.antMatchers("/resources/**", "/webjars/**").permitAll()
	            // Para qualquer requisição (anyRequest) é preciso estar 
	            // autenticado (authenticated).
	            .anyRequest().authenticated()
	        .and()
	        .httpBasic();
	  }
	
	@Override
	  public void configure(AuthenticationManagerBuilder builder) throws Exception {
	    builder
	        .inMemoryAuthentication()
	        .withUser("erik").password("123")
	            .roles("ADMIN")
	        .and()
	        .withUser("fernanda").password("123")
	            .roles("ADMIN")
	        .and()
		    .withUser("victor").password("123")
		        .roles("ADMIN");  
	  }
	
	@Bean
	  public PasswordEncoder passwordEncoder() {
	      return NoOpPasswordEncoder.getInstance();
	  }
}
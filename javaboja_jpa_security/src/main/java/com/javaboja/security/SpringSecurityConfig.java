package com.javaboja.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	AuthFailureHandler authFailureHandler;
	@Autowired
	AuthSuccessHandler authSuccessHandler;
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
	    // h2 console 사용을 위한 설정 
		/*
		 * http.csrf().ignoringAntMatchers("/h2console/**");
		 * http.headers().frameOptions().sameOrigin();
		 */
		http
		  .authorizeRequests()
		      // user 폴더에 경우 user 권한이 있는 사용자에게만 허용
		  	.anyRequest().authenticated()
		    .and()
		  .formLogin()
		    .loginPage("/login")
		    .defaultSuccessUrl("/main", true)
		    .successHandler(authSuccessHandler)
		    .failureHandler(authFailureHandler)
		    .permitAll()
		    .and()
		  .logout()
		  	.logoutSuccessUrl("/login")
		  	.invalidateHttpSession(true)
		    .permitAll()
		    .and()
		   .exceptionHandling().accessDeniedPage("/403");
		/*http
				.authorizeRequests()
				.antMatchers("/**")
						.permitAll()
				.and()
		.formLogin()
						.usernameParameter("userId")
						.usernameParameter("userPassword")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/main")
						.permitAll()
				.and()
		.logout()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
						.permitAll()
				.and()
		.csrf().disable();*/
	  }
	  
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/resources/**",
				   "/css/**",
				   "/fonts/**",
				   "/js/**",
				   "/loginError",
				   "/registration",
				   "/h2console/**");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	      auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	  
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception { 
	  return super.authenticationManagerBean();
	}
	
}

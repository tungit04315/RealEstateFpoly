package com.poly.config;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.core.Authentication;

import com.poly.bean.Users;
import com.poly.service.UsersService;
import com.poly.util.SessionService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	SessionService session;
	
	// Password encryption mechanism
	@Bean
	public BCryptPasswordEncoder getpaBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Provide login data
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(
				username -> {
				try {
					Users user = userService.findById(username);
					if(!user.isActive()) {
						System.out.println("Tài khoản chưa kích hoạt");
						throw new DisabledException("Tài khoản chưa kích hoạt");
					}
					String[] roles = user.getAuth().stream().map(ro -> ro.getRoles().getRoles_id())
									.collect(Collectors.toList()).toArray(new String[0]);
					
					Map<String, Object> authentication = new HashMap<>();
					
					byte[] token = (username + ":" + user.getPasswords()).getBytes();
					authentication.put("user", user);
					authentication.put("token", "Basic " + Base64.getEncoder().encodeToString(token));
					//Lưu tài khoản vào session
					session.setAttribute("user", user);
					session.setAttribute("authentication", authentication);
					//Lưu tài khoản vào session
					
					System.out.println(username + "Username");
					System.out.println();
					System.out.println(user.getPasswords() + "Password");
					
					return User.withUsername(username).password(user.getPasswords()).roles(roles).build();
				} catch (Exception e) {
					throw new UsernameNotFoundException(username + " Not Found!!! 404");
				}
			}
		);
	}

	// Cho phép truy cập restfull từ tên miền khác
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	// Authorization of use
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.authorizeRequests()
		.antMatchers("/post/**","/user/**","/home/manager/**").authenticated()
		.antMatchers("/admin-2/**").hasAnyRole("ADMIN")
		.antMatchers("/rest/authorities").hasRole("USER")
		.anyRequest().permitAll();
		
		http.formLogin().loginPage("/login")
		.loginProcessingUrl("/login/action-test")
		.defaultSuccessUrl("/login/action/success", false)
		.failureUrl("/login/action/error");
		
		
		http.rememberMe().tokenValiditySeconds(86400);
		
		http.exceptionHandling().accessDeniedPage("/home/error");
// 		OAuth2- Đăng nhâp từ mang xã hôi
		http.oauth2Login().loginPage("/login")
			.defaultSuccessUrl("/oauth2/login/success", true)
			.failureUrl("/auth/login/error")
			.authorizationEndpoint()
			.baseUri("/oauth2/authorization");
		
		
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/logout/success");
	}
}

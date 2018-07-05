package osg.loki.simple_auth.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import osg.loki.simple_auth.controller.LoggingAccessDeniedHandler;
import osg.loki.simple_auth.service.CustomUserDetailsService;
/*
@Configuration
@EnableWebSecurity*/
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/*@Autowired CustomUserDetailsService customUserDetailsService;
	@Autowired LoggingAccessDeniedHandler accessDeniedHandler;
	@Autowired AuthSuccessHandler successHandler;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}/*
	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {	
	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}*/
	/*
	 @Override
	  protected void configure(HttpSecurity http) throws Exception {	 
	    http    
	    .authorizeRequests()	    	
	    .antMatchers("/static/css/**").permitAll()	    
	    	.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).anonymous()	        
	        .antMatchers(HttpMethod.POST, "/login").permitAll()	        
	        .antMatchers("/signup").permitAll()
	        .antMatchers("/confirm").permitAll()
	        .antMatchers("/admin/**").hasRole("ADMIN")
	        .anyRequest().authenticated()
	        .and()	        
	        .formLogin()
	        	.loginPage("/admin/login")
	        	.successHandler(successHandler)
	        	.permitAll()
	        .and()
	        .logout()
	        	.invalidateHttpSession(true)
	        	.clearAuthentication(true)
	        	.logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
	        	.logoutSuccessUrl("/admin/login?logout")
	        	.permitAll()
	        .and()
	        .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
	        .and()
	        // We filter the api/login requests
	        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
	                UsernamePasswordAuthenticationFilter.class)
	        // And filter other requests to check the presence of JWT in header
	        .addFilterAfter(new JWTAuthenticationFilter(),
	                UsernamePasswordAuthenticationFilter.class);
	    
	  }

	  @Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		
		web.ignoring().antMatchers("/admin","/admin/**");
		
		
	}

	@Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    // Create a default account
		 auth.userDetailsService(customUserDetailsService);
		 /*.and()
		 .inMemoryAuthentication()
		 	.withUser("admin").password("admin").roles("ADMIN");
	    /*auth.inMemoryAuthentication()
	        .withUser("administrator")
	        .password("password")
	        
	        .roles("ADMIN");*/
	  /*}*/


}

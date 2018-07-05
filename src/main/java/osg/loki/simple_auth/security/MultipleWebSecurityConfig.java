package osg.loki.simple_auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import osg.loki.simple_auth.controller.LoggingAccessDeniedHandler;
import osg.loki.simple_auth.service.CustomUserDetailsService;

@EnableWebSecurity

public class MultipleWebSecurityConfig  {

	@Configuration
	@Order(1)
	public static class PermitAllWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}
		@Autowired CustomUserDetailsService customUserDetailsService;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/auth/**").authorizeRequests().anyRequest().permitAll()
			
			
			.antMatchers("/static/css/**").permitAll()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			.and().csrf().disable().addFilterBefore(new JWTLoginFilter("/auth/login", authenticationManager()),
	                UsernamePasswordAuthenticationFilter.class);
			
		}
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			 auth.userDetailsService(customUserDetailsService);		
		}
		
	}
	
	@Configuration
	@Order(2)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{

		@Autowired CustomUserDetailsService customUserDetailsService;
		
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			System.out.println("api");
			http    
		    .antMatcher("/api/**").authorizeRequests().anyRequest().authenticated()	       
		        
		        .and()	          // We filter the api/login requests
		        .csrf().disable()
		        
		        // And filter other requests to check the presence of JWT in header
		        .addFilterAfter(new JWTAuthenticationFilter(),
		                UsernamePasswordAuthenticationFilter.class);
		        
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			 auth.userDetailsService(customUserDetailsService);		
		}
		
		
	}
	@Configuration
	
	public static class FormLoginWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter{

		@Autowired LoggingAccessDeniedHandler accessDeniedHandler;
		@Autowired AuthSuccessHandler successHandler;
		@SuppressWarnings("deprecation")
		@Bean
		public static NoOpPasswordEncoder passwordEncoder() {	
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			System.out.println("form");
			http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
			 .antMatchers("/static/css/**").permitAll()	    
		    	.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		    
	        
	        .and().formLogin()
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
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		}
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
		 	.withUser("admin").password("admin").roles("ADMIN");
		}
		
	}
}

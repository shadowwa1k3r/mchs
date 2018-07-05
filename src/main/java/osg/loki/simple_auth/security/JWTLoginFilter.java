package osg.loki.simple_auth.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import osg.loki.simple_auth.model.AccountCredentials;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	private Logger log = LoggerFactory.getLogger(getClass());
	public JWTLoginFilter(String url, AuthenticationManager authManager) {
	    super(new AntPathRequestMatcher(url,HttpMethod.POST.toString()));
	    setAuthenticationManager(authManager);
	  }
	
	@Override
	  public Authentication attemptAuthentication(
	      HttpServletRequest req, HttpServletResponse res)
	      throws AuthenticationException, IOException, ServletException {
		
	    AccountCredentials creds = new ObjectMapper()
	        .readValue(req.getInputStream(), AccountCredentials.class);
	    log.info("login attempt "+creds.getUsername());	    
	    	if (creds.getUsername().equals("321")) throw new javax.security.sasl.AuthenticationException("user not activated");	    
	    try {
	    	
	    	return getAuthenticationManager().authenticate(
	        new UsernamePasswordAuthenticationToken(
	            creds.getUsername(),
	            creds.getPassword(),
	            Collections.emptyList()
	        )
	    );}
	    catch(AuthenticationException e) {
	    	log.error(e.getMessage());
	    	
	    	return null;
	    }
	    
	  }

	
	 @Override
	  protected void successfulAuthentication(
	      HttpServletRequest req,
	      HttpServletResponse res, FilterChain chain,
	      Authentication auth) throws IOException, ServletException {
		 log.info("login succes "+auth.getName());
	    TokenAuthenticationService
	        .addAuthentication(res, auth.getName());
	  }



}

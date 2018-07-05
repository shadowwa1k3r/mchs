package osg.loki.simple_auth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.MalformedJwtException;

public class JWTAuthenticationFilter extends GenericFilterBean{
	Logger log = LoggerFactory.getLogger(getClass());
	@Override
	  public void doFilter(ServletRequest request,
	             ServletResponse response,
	             FilterChain filterChain)
	      throws IOException, ServletException {
		
	    try{Authentication authentication = TokenAuthenticationService
	        .getAuthentication((HttpServletRequest)request);
	    
	    SecurityContextHolder.getContext()
	        .setAuthentication(authentication);
	   /* try {
	    log.info("auth "+authentication.getName());
	    	//System.out.println(authentication.getName()+" "+authentication.getCredentials());
	    }
	    catch(Exception e) {
	    	
	    	//log.error(e.getCause().toString());
	    	log.error(e.getMessage());
	    }*/
	    filterChain.doFilter(request,response);}
	    catch (MalformedJwtException e) {
	    	LoggerFactory.getLogger("auth").error(e.getMessage());
	    	HttpServletResponse resp = (HttpServletResponse)response;
	    	resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	
	    	filterChain.doFilter(request, resp);
	    	
	    	 
		}
	  }


}

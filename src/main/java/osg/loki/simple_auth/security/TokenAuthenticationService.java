package osg.loki.simple_auth.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.ContainerType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonParseException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import osg.loki.simple_auth.model.TokenResponse;

import static java.util.Collections.emptyList;

import java.io.IOException;

public class TokenAuthenticationService {
	  static final long EXPIRATIONTIME = 1_864_000_000; // 10 days
	  static final String SECRET = "ThisIsASecret";
	  static final String TOKEN_PREFIX = "Bearer";
	  static final String HEADER_STRING = "Authorization";
	  
	   

	  static void addAuthentication(HttpServletResponse res, String username) {
		    String JWT = Jwts.builder()
		        .setSubject(username)
		        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
		        .signWith(SignatureAlgorithm.HS512, SECRET)
		        .compact();
		    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		    System.out.println(JWT);
		    try {
		    	res.setContentType("application/json");
				res.getWriter().println("{\"token\":\""+JWT+"\"}");;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		  }
	   public static TokenResponse getToken(String username) {
		  String JWT = Jwts.builder()
			        .setSubject(username)
			        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
			        .signWith(SignatureAlgorithm.HS512, SECRET)
			        .compact();
		  return new TokenResponse(JWT);
	  }
	   public static String getUsername(String token) {
		   System.out.println(token);
		   String user = Jwts.parser()
			          .setSigningKey(SECRET)
			          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
			          .getBody()
			          .getSubject();
		   System.out.println(user);
		   return user;
	   }

	  
	  static Authentication getAuthentication(HttpServletRequest request) throws JsonParseException,SignatureException,MalformedJwtException {
		    String token = request.getHeader(HEADER_STRING);
		    
		    if (token != null) {
		      String user = Jwts.parser()
		          .setSigningKey(SECRET)
		          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
		          .getBody()
		          .getSubject();

		      return user != null ?
		          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
		          null;
		    }
		    return null;
		  }


}

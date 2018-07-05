package osg.loki.simple_auth.controller;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler {

	private static Logger log = LoggerFactory.getLogger(LoggingAccessDeniedHandler.class);
		
	

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.access.AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null) {
			log.info(auth.getName()+"was trying to access adminpage"+request.getRequestURI());
			
		}
		response.sendRedirect("admin/access-denied");
		
	}
}

package osg.loki.simple_auth.model;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class Response extends HttpServletResponseWrapper {

	public Response(HttpServletResponse response) {
		super(response);
		
		// TODO Auto-generated constructor stub
	}

}

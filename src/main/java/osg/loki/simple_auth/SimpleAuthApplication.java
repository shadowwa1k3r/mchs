package osg.loki.simple_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SimpleAuthApplication /* extends SpringBootServletInitializer*/{

	
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	
		return builder.sources(SimpleAuthApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(SimpleAuthApplication.class, args);
	}
}

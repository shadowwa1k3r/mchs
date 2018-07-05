package osg.loki.simple_auth.model;

public class TokenResponse {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenResponse(String token) {
		super();
		this.token = token;
	}
	

}

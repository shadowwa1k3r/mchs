package osg.loki.simple_auth.model;

public class UserDBModel {
	private String username,password; private Integer temp_code;
	private Boolean enabled,notLocked;
	public String getUsername() {
		return username;
	}
	public UserDBModel(String username, String password, Integer temp_code, Boolean enabled, Boolean notLocked) {
		super();
		this.username = username;
		this.password = password;
		this.temp_code = temp_code;
		this.enabled = enabled;
		this.notLocked = notLocked;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getTemp_code() {
		return temp_code;
	}
	public void setTemp_code(Integer temp_code) {
		this.temp_code = temp_code;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Boolean getNotLocked() {
		return notLocked;
	}
	public void setNotLocked(Boolean notLocked) {
		this.notLocked = notLocked;
	}
	

}

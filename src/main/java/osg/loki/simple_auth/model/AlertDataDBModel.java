package osg.loki.simple_auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlertDataDBModel {
	private String title,body,sender,status,imgs;
	private Integer id;
	private Double longitude,altitude;
	private String date;
	private List<String> files=new ArrayList<String>();
	
	public List<String> getImgs() {
		return files;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
		if(imgs.length()>0) {
		String[] media = imgs.split(";");
		for(int i=0;i<media.length;i++) {
			files.add(media[i]);
		}
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	

}

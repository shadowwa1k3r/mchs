package osg.loki.simple_auth.repository;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import osg.loki.simple_auth.controller.SendHttpRequestForSms;
import osg.loki.simple_auth.model.AlertDataDBModel;
import osg.loki.simple_auth.model.AlertDataModel;
import osg.loki.simple_auth.model.InfoModel;
import osg.loki.simple_auth.model.UserDBModel;
import osg.loki.simple_auth.security.TokenAuthenticationService;

@Repository
public class UserRepository {
	@Autowired JdbcTemplate jdbcTemplate;
	BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	Logger log=LoggerFactory.getLogger(getClass());
	
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	

	
	public UserDBModel findUserByName(String name) {
		String sql = "select * from mchs_users where username='"+name+"'";
		Map<String,Object> row = jdbcTemplate.queryForMap(sql);
		return new UserDBModel((String)row.get("username"), (String)row.get("password"), (Integer)row.get("temp_code"), Boolean.parseBoolean((String)row.get("enabled")), Boolean.parseBoolean((String)row.get("locked")));
	}
	public String saveUser(String username,String password) {	
		SecureRandom random = new SecureRandom();
		String confirmCode=""+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);		
		String sql = "insert into mchs_users values(nextval('mchs_users_id_seq'),'false','true','"+username+"','"+encoder.encode(password)+"',"+confirmCode+")";
		try {
			jdbcTemplate.execute(sql);
			SendHttpRequestForSms.SendSms(confirmCode, username);
			return confirmCode;
		} catch(Exception e) {
			log.error(e.getMessage());
			return "exist";
		}
				
		
	}
	public Boolean activateUser(String username,int code) {
		String sql = "update mchs_users set enabled='true' where username='"+username+"' and temp_code="+code;
		try{if(jdbcTemplate.update(sql)!=0) {
			return true;
		}
		else return false;}
		catch(Exception e) {
			log.error(e.getMessage());
			return false;
		}
		
	}
	public List<UserDBModel> userList(){
		String sql = "select * from mchs_users";
		List<UserDBModel> users= new ArrayList<>();
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		for(Map<String,Object> row: rows) {
			UserDBModel user = new UserDBModel((String)row.get("username"), (String)row.get("password"), (Integer)row.get("temp_code"), Boolean.parseBoolean((String)row.get("enabled")), Boolean.parseBoolean((String)row.get("locked")));
			users.add(user);					
		}
		
		return users;
	}
	public List<AlertDataDBModel> messageList(){
		String sql="select * from mchs_alert_messages order by date asc";
		List<AlertDataDBModel> messages = new ArrayList<>();
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		
		for(Map<String,Object> row:rows) {
			AlertDataDBModel message = new AlertDataDBModel();
			message.setId((Integer)row.get("id"));
			message.setTitle((String)row.get("title"));
			message.setBody((String)row.get("body"));
			message.setSender((String)row.get("sender"));
			message.setAltitude(Double.parseDouble((String) row.get("altitude")));
			message.setLongitude(Double.parseDouble((String) row.get("longitude")));
			message.setDate((String)row.get("date"));
			message.setStatus((String)row.get("status"));
			messages.add(message);
		}		
		return messages;
		
	}
	public AlertDataDBModel getMessage(int id) {
		String sql = "select * from mchs_alert_messages where id="+id;
		Map<String,Object> row = jdbcTemplate.queryForMap(sql);
		AlertDataDBModel message = new AlertDataDBModel();
		message.setId((Integer)row.get("id"));
		message.setTitle((String)row.get("title"));
		message.setBody((String)row.get("body"));
		message.setSender((String)row.get("sender"));
		message.setAltitude(Double.parseDouble((String) row.get("altitude")));
		message.setLongitude(Double.parseDouble((String) row.get("longitude")));
		message.setDate((String)row.get("date"));
		message.setStatus((String)row.get("status"));
		message.setImgs((String)row.get("media"));
		if(message.getStatus().equals("не прочитано")) {
			String sql2="update mchs_alert_messages set status='прочитано' where id="+id;
			jdbcTemplate.execute(sql2);
		}
		
		return message;
	}
	public int newMessageCount() {
		String sql = "select * from mchs_alert_messages where status='не прочитано'";
		
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		
		return rows.size();
	}
	public void addDataToHelpCenter(InfoModel info) {
		Calendar cal = Calendar.getInstance();
		if(info.getId()==null) {
		String sql = "insert into mchs_wikipedia values(nextval('mchs_wikipedia_id_seq'::regclass),'"+
		info.getTitle()+"','"+info.getContent()+"','"+sdf.format(cal.getTime())+"','"+info.getIcon()+"','"+info.getImage()+"')";
		jdbcTemplate.execute(sql);
		log.info(info.getTitle()+info.getContent());}
		else {
			String sql = "update mchs_wikipedia set title='"+info.getTitle()+"',content='"+info.getContent()+"',"
					+ "last_modified='"+sdf.format(cal.getTime())+"',icon='"+info.getIcon()+"',image='"+info.getImage()+"' where id="+info.getId();
			jdbcTemplate.execute(sql);
			
		}
	}
	
	public void dropInfo(Integer id) {
		String sql = "delete from mchs_wikipedia where id="+id;
		jdbcTemplate.execute(sql);
	}
	
	
	public List<InfoModel> getDataFromHelpCenter(){
		String sql = "select * from mchs_wikipedia";
		List<InfoModel> data = new ArrayList<>();
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		
		for(Map<String,Object> row:rows) {
			InfoModel info = new InfoModel();
			info.setId((Integer)row.get("id"));
			info.setTitle((String)row.get("title"));
			info.setContent((String)row.get("content"));
			info.setLast_modified((String)row.get("last_modified"));
			info.setIcon((String)row.get("icon"));
			info.setImage((String)row.get("image"));
			data.add(info);
		}
		log.info(data.size()+" ");
		return data;
	}
	public void saveMessage(AlertDataModel data, String header, List<String> filelist) {
		
		//log.info(""+data.getLocation()[0]+data.getLocation()[1]);
		Calendar cal = Calendar.getInstance();
		String imgs="";
		for(int i=0;i<filelist.size();i++) {
			imgs=imgs+filelist.get(i)+";";			
		}

		String sql = "insert into mchs_alert_messages values(nextval('mchs_alert_messages_id_seq'::regclass),'"+data.getType()+"','"+data.getAbout()+"',"
				+ "'"+TokenAuthenticationService.getUsername(header)+"','не прочитано','"+data.getLongitude()+"','"+data.getAltitude()+"','"+sdf.format(cal.getTime())+"','"+imgs+"')";
		
		jdbcTemplate.execute(sql);
		
		
		
	}
	public InfoModel getWikiInfo(Integer id) {
		String sql = "select * from mchs_wikipedia where id="+id;
		Map<String,Object> row = jdbcTemplate.queryForMap(sql);
		InfoModel info = new InfoModel();
		info.setId((Integer)row.get("id"));
		info.setTitle((String)row.get("title"));
		info.setContent((String)row.get("content"));
		info.setIcon((String)row.get("icon"));
		info.setImage((String)row.get("image"));
		info.setLast_modified((String)row.get("last_modified"));
		
		return info;
	}
	
	

}

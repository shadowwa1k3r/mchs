package osg.loki.simple_auth.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import osg.loki.simple_auth.model.AlertDataModel;
import osg.loki.simple_auth.model.ReportResponse;
import osg.loki.simple_auth.model.UserDBModel;
import osg.loki.simple_auth.model.UserRegisterModel;
import osg.loki.simple_auth.repository.UserRepository;
import osg.loki.simple_auth.security.TokenAuthenticationService;

@RestController
public class UserController {
	@Autowired UserRepository userRepository;
	private static String UPLOADED_FOLDER = "C:\\Users\\ergas\\Desktop\\simple_auth\\src\\main\\resources\\static\\img\\";
	@RequestMapping("/hello")
	public String hello(@RequestHeader HttpHeaders headers) {
		
		return "Hello "+headers.toString();
	}
	
	@RequestMapping("/auth/users")
	public 	@ResponseBody String getUsers() {
		return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
		           "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/auth/signup",method=RequestMethod.POST)
	public ResponseEntity signup(@RequestBody UserRegisterModel user ) {
		String confirmCode=userRepository.saveUser(user.getUsername(), user.getPassword());
		System.out.println(confirmCode);
		
		if(confirmCode.equals("exist")) return new ResponseEntity("user exist",HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity("error",HttpStatus.CREATED);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/auth/confirm",method=RequestMethod.GET)
	public ResponseEntity confirm (@RequestParam("username")String username,
			@RequestParam("confirm_code")int code) {
		
		if(userRepository.activateUser(username, code)) {
			return new ResponseEntity(TokenAuthenticationService.getToken(username),HttpStatus.ACCEPTED);		
		}
		else {
			return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@RequestMapping(value = "/api/alert",method = RequestMethod.POST)
	public String newAlert(@RequestHeader("Authorization")String header,@RequestPart("data") AlertDataModel data, @RequestParam("file")List<MultipartFile> file) {
		
		System.out.println(header);
		List<String> filelist = new ArrayList<>();
		for(int i=0;i<file.size();i++) {
			System.out.println(file.get(i).getContentType()+" "+file.get(i).getOriginalFilename()+file.get(i).getSize());
			try{
				byte[] bytes = file.get(i).getBytes();
				Path path = Paths.get(UPLOADED_FOLDER+file.get(i).getOriginalFilename());
				
				Files.write(path, bytes);
				filelist.add("http://192.168.1.112:8081/img/"+file.get(i).getOriginalFilename());
				
			}
			catch(IOException e) {
				e.printStackTrace();				
			}}
		userRepository.saveMessage(data,header,filelist);
		
		System.out.println(file.size());
		System.out.println(data.getAltitude()+" "+data.getLongitude()+data.getName());		
		return "{\"answer\":\"ok\"}";		
		
	}
	@GetMapping ("/api/send")
	public String test() {
		//System.out.println(header+" "+body);
		return "{\\\"answer\\\":\\\"ok\\\"}";
	}
	@RequestMapping(value="/admin/uploadpic", method=RequestMethod.POST)
	public ResponseEntity uploadpic(@RequestParam("picture")MultipartFile file) {
		System.out.println(file.getSize());
		return new ResponseEntity("accepted",HttpStatus.ACCEPTED);
	}
	

}

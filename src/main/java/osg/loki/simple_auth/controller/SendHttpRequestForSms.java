package osg.loki.simple_auth.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import osg.loki.simple_auth.model.SmsModel;

public class SendHttpRequestForSms {
	private static final Logger log = LoggerFactory.getLogger(SendHttpRequestForSms.class);
	
	public static void SendSms(String text,String number) throws Exception {
		URL obj = new URL("https://lcab.smsint.ru/lcabApi/sendSms.php?login=protimaru_farrukh&password=pr9t1maru&txt="+text+"&to="+number);
		HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
		
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine())!=null) {
			response.append(inputLine);
		}
		in.close();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = response.toString();
		log.info(jsonString);
		try {
			SmsModel sms = mapper.readValue(jsonString,SmsModel.class);
			
		}
		catch(JsonParseException e) {log.error(e.getMessage());}
	    catch(JsonMappingException e) {log.error(e.getMessage());}
	    catch(IOException e) {log.error(e.getMessage());}
	}

}

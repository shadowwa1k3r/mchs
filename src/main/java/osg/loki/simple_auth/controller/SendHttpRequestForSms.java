package osg.loki.simple_auth.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
	
	public static String sendSms(String text,String number) {
		try {
			// Construct data
			String apiKey = "apikey=" + "fSSO4wI3D+s-LAaGQh2x3serszTmcWwFh6dg0fU7mE";
			String message = "&message=" + text;
			String sender = "&sender=" + "OSG Test";
			String numbers = "&numbers=" + number;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			System.out.println(stringBuffer.toString());
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}

}

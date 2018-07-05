package osg.loki.simple_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import osg.loki.simple_auth.model.InfoModel;
import osg.loki.simple_auth.repository.UserRepository;

@Controller
public class AdminController {
	@Autowired UserRepository userRepository;
	
	@RequestMapping(value="/admin/userlist",method=RequestMethod.GET)
	public String userList(Model model) {
		model.addAttribute("users", userRepository.userList());
		return "userList";
	}
	@RequestMapping(value="/admin/helplist", method = RequestMethod.GET)
	public String helplist(Model model) {
		model.addAttribute("wikipedia", userRepository.getDataFromHelpCenter());
		return "helplist";
	}
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String dashboard(Model model) {
		//model.addAttribute("users", userRepository.userList());
		model.addAttribute("msgcount", userRepository.newMessageCount());
		return "DashBoard";
	}
	@RequestMapping(value="/admin/alertlist",method=RequestMethod.GET)
	public String alertList(Model model) {
		model.addAttribute("messagesList", userRepository.messageList());
		
		return "alertList";
	}
	@RequestMapping(value = "/admin/alertlist/{message_id}",method=RequestMethod.GET)
	public String message(Model model,@PathVariable("message_id") int id) {
		model.addAttribute("message", userRepository.getMessage(id));						
		return "message";		
	}
	@RequestMapping(value= {"/admin/addinfo","/admin/addinfo/{id}"},method=RequestMethod.GET)
	public String info(Model model,@PathVariable(required=false) Integer id) {
		if(id==null) {
		model.addAttribute("info", new InfoModel());
		return "info";}
		else {
			model.addAttribute("info",userRepository.getWikiInfo(id));
			return "info";
		}
	}
	
	@RequestMapping(value="/admin/helplist/{id}")
	public String showinfo(Model model,@PathVariable Integer id) {	
		model.addAttribute("info", userRepository.getWikiInfo(id));

		return "wikishow";
	}
	
	@RequestMapping(value="/admin/infoEdit",method=RequestMethod.POST)
	public String save(Model model, InfoModel info) {
		userRepository.addDataToHelpCenter(info);
		return "redirect:/admin/helplist";
	}
	@RequestMapping(value="/admin/dropinfo/{id}",method=RequestMethod.GET)
	public String dropInfo(@PathVariable Integer id) {
		userRepository.dropInfo(id);
		return "redirect:/admin/helplist";
	}

	
	@GetMapping("/admin/login")
	public String login() {
		System.out.println("login");
		return "login";
	}
	@GetMapping("/admin/access-denied")
	public String denied() {
		return "access-denied";
	}
	@GetMapping("/")
	public String root() {
		return "redirect:/admin";
	}

}

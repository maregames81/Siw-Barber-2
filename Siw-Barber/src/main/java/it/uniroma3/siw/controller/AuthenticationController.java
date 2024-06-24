package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ServizioService;
import it.uniroma3.siw.service.UserService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;

    @Autowired
	private UserService userService;
    
    @Autowired
   	private ServizioService serviceService;
    
    
    @GetMapping("/")
    public String showIndex(Model model) {
    	
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
    		
    		model.addAttribute("servizi", this.serviceService.findAll());
    		model.addAttribute("operatori", this.userService.findByRole(Credentials.ADMIN_ROLE));
            return "admin/index.html";
        }
    	model.addAttribute("servizi", this.serviceService.findAll());
		model.addAttribute("operatori", this.userService.findByRole(Credentials.ADMIN_ROLE));
        return "index.html";
    }
    
    @GetMapping("/login") 
	public String showLogin(Model model) {
		return "formLogin.html";
	} 
    
    
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/index.html";
        }
        return "index.html";
    }
    
    
    @GetMapping("/register")
    public String showRegister(Model model) {
    	
    	model.addAttribute("user", new User());
    	model.addAttribute("credentials", new Credentials());
    	
    	return "formRegisterUser.html";
    }
    
    
}

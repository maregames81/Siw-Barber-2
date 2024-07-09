package it.uniroma3.siw.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.ServizioValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Servizio;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.service.ServizioService;
import jakarta.validation.Valid;


@Controller
public class ServizioController {

	@Autowired
	private ServizioService servizioService;

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private ServizioValidator servizioValidator;

	@Autowired
	private PrenotazioneService prenotazioneService;
	
	
	
	
	  private List<User> trovaOperatori() {

	    	Iterable<Credentials> credenzialiAdmin= this.credentialsService.findByRole(Credentials.ADMIN_ROLE);

	    	List<User> operatori = new ArrayList<>();
			for(Credentials c : credenzialiAdmin) {
				operatori.add(c.getUser());
			}
		
		return operatori;
	}
	


	@PostMapping("/newServizio")
	public String newServizio(@Valid @ModelAttribute("servizio") Servizio servizio,BindingResult bindingResult,
			Model model) {

		this.servizioValidator.validate(servizio, bindingResult);

		if(!bindingResult.hasErrors()) {
			this.servizioService.save(servizio);
			return "redirect:/";
		}
		
		
		
		
		
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	
    		model.addAttribute("servizi", this.servizioService.findAll());
    		model.addAttribute("operatori",this.trovaOperatori());
    		model.addAttribute("user", credentials.getUser());
    		
    		LocalDateTime attuale = LocalDateTime.now();
    		model.addAttribute("prenotazioni", this.prenotazioneService.findByDataGreaterThan(attuale));
    		
		return "admin/indexOperatore.html";
	}


	@PostMapping("/admin/modificaPrezzo")
	public String modificaPrezzo(@RequestParam("idServizio") Long idS,
			@RequestParam("nuovoPrezzo") float newPrezzo, Model model) {


		Servizio s= this.servizioService.findById(idS);
		s.setPrezzo(newPrezzo);

		this.servizioService.save(s);

		return "redirect:/";
	}

}

package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Servizio;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ServizioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ServizioController {

	@Autowired
	private ServizioService servizioService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	
	@PostMapping("/newServizio")
	public String newServizio(@Valid @ModelAttribute("servizio") Servizio servizio,BindingResult bindingResult,
			Model model) {
		
			this.servizioService.save(servizio);
		
		return "redirect:/";
	}
	
	
	
	@PostMapping("/modificaPrezzo")
	public String modificaPrezzo(@RequestParam("idServizio") Long idS,
								@RequestParam("nuovoPrezzo") float newPrezzo, Model model) {
		
		
		Servizio s= this.servizioService.findById(idS);
		System.out.println(s.getNome());
		s.setPrezzo(newPrezzo);
		
		this.servizioService.save(s);
		
		return "redirect:/";
	}
	
}

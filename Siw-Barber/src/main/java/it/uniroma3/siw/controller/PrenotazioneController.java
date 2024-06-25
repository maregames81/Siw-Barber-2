package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.service.ServizioService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;


@Controller
public class PrenotazioneController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;

	@Autowired
	private ServizioService servizioService;

	@Autowired
	private PrenotazioneService prenotazioneService;



	@GetMapping("/indexPrenotazione")
	public String newPrenotazione(@ModelAttribute("userDetails") UserDetails userD,Model model) {

		String username = userD.getUsername();    	
		Credentials credentials= this.credentialsService.getCredentials(username);    	
		User u = credentials.getUser();

		Iterable<Credentials> credenzialiAdmin= this.credentialsService.findByRole(Credentials.ADMIN_ROLE);

		List<User> operatori = new ArrayList<>();
		for(Credentials c : credenzialiAdmin) {
			operatori.add(c.getUser());
		}





		model.addAttribute("user", u);
		model.addAttribute("operatori", operatori);
		model.addAttribute("servizi", this.servizioService.findAll());
		model.addAttribute("prenotazione", new Prenotazione());

		return "indexPrenotazione.html";
	}


	@PostMapping("/indexPrenotazione")
	public String newPrenotazione(@Valid @ModelAttribute("prenotazione") Prenotazione p,BindingResult bindingResult,
			@ModelAttribute("userDetails") UserDetails userD, Model model) {

		String username = userD.getUsername();
		User u= credentialsService.getCredentials(username).getUser();

		p.setCliente(u);

		if(!bindingResult.hasErrors()) {
			this.prenotazioneService.save(p);
		}

		return "";
	}

}

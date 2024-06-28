package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.PrenotazioneValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.Servizio;
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

	@Autowired
	private PrenotazioneValidator prenotazioneValidator;
	
	
	
	
	private List<User> trovaOperatori() {

    	Iterable<Credentials> credenzialiAdmin= this.credentialsService.findByRole(Credentials.ADMIN_ROLE);

    	List<User> operatori = new ArrayList<>();
		for(Credentials c : credenzialiAdmin) {
			operatori.add(c.getUser());
		}
	
	return operatori;
}



	@GetMapping("/indexPrenotazione")
	public String newPrenotazione(@ModelAttribute("userDetails") UserDetails userD,Model model) {

		String username = userD.getUsername();    	
		Credentials credentials= this.credentialsService.getCredentials(username);    	
		User cliente = credentials.getUser();




		LocalDateTime attuale = LocalDateTime.now();
		model.addAttribute("prenotazioni", this.prenotazioneService.findByDataGreaterThanAndCliente(attuale,cliente));

		model.addAttribute("user", cliente);
		model.addAttribute("operatori", this.trovaOperatori());
		model.addAttribute("servizi", this.servizioService.findAll());
		model.addAttribute("prenotazione", new Prenotazione());

		return "indexPrenotazione.html";
	}


	@PostMapping("/indexPrenotazione")
	public String newPrenotazione(@Valid @ModelAttribute("prenotazione") Prenotazione p,BindingResult bindingResult,
			@ModelAttribute("userDetails") UserDetails userD,
			@RequestParam("IdOperatore") Long idOperatore,
			@RequestParam("IdServizio") Long idServizio,
			@RequestParam("dataPren") LocalDate data,
			@RequestParam("orarioPren") LocalTime orario, Model model) {

		//Settaggio del cliente prenotante
		String username = userD.getUsername();
		User u= credentialsService.getCredentials(username).getUser();
		p.setCliente(u);


		//Settaggio operatore della prenotazione
		User operatore= this.userService.getUser(idOperatore);
		p.setOperatore(operatore);

		//Settaggio servizio
		Servizio s= this.servizioService.findById(idServizio);
		p.setServizio(s);

		//Settaggio orario e data
		p.setOrario(LocalDateTime.of(data, orario));


		this.prenotazioneValidator.validate(p,bindingResult);

		//Se non ci sono errori allora salvo
		if(!bindingResult.hasErrors()) {
			this.prenotazioneService.save(p);
			return "redirect:/indexPrenotazione";
		}
		
		//Se sono qui significa che ci sono errori quindi riaggiungo i vari elementi al model
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	User cliente = credentials.getUser();


		LocalDateTime attuale = LocalDateTime.now();
		model.addAttribute("prenotazioni", this.prenotazioneService.findByDataGreaterThanAndCliente(attuale,cliente));

		model.addAttribute("user", cliente);
		model.addAttribute("operatori", this.trovaOperatori());
		model.addAttribute("servizi", this.servizioService.findAll());
    		
		return "indexPrenotazione.html";
	}


	@GetMapping("/disdiciPrenotazione/{id}")
	public String disdiciPrenotazione(@PathVariable("id") Long idP, Model model) {

		this.prenotazioneService.delete(idP);

		return "redirect:/indexPrenotazione";
	}


	@GetMapping("/disdiciPrenotazioneOperatore/{id}")
	public String disdiciPrenotazioneOperatore(@PathVariable("id") Long idP, Model model) {

		this.prenotazioneService.delete(idP);

		return "redirect:/";
	}

}

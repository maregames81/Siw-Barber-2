package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.Servizio;
import it.uniroma3.siw.service.PrenotazioneService;

@Component
public class PrenotazioneValidator implements Validator {
	
	@Autowired
	private PrenotazioneService prenotazioneService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Prenotazione.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Prenotazione prenotazione = (Prenotazione)target;
		if (prenotazione.getOperatore()!=null && prenotazione.getOrario()!=null
				&& this.prenotazioneService.existByOrarioAndOperatore(prenotazione.getOrario(),prenotazione.getOperatore())) {
			errors.rejectValue("orario", "prenotazione.duplicate");
		}
		
	}


}

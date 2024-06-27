package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Servizio;
import it.uniroma3.siw.service.ServizioService;

@Component
public class ServizioValidator implements Validator {
	
	
	@Autowired ServizioService servizioService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Servizio.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Servizio servizio = (Servizio)target;
		if (servizio.getNome()!=null && this.servizioService.existByNome(servizio.getNome())) {
			errors.rejectValue("nome", "servizio.duplicate");
		}
		
	}

}

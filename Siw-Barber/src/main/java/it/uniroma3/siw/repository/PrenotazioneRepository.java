package it.uniroma3.siw.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

	List<Prenotazione> findByOrarioGreaterThan(LocalDateTime data);
	
	List<Prenotazione> findByOrarioGreaterThanAndOperatore(LocalDateTime data, User operatore);
	
	List<Prenotazione> findByOrarioGreaterThanAndCliente(LocalDateTime data, User cliente);
}

package it.uniroma3.siw.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

	List<Prenotazione> findByOrarioGreaterThanOrderByOrarioAsc(LocalDateTime data);
	
	List<Prenotazione> findByOrarioGreaterThanAndOperatoreOrderByOrarioAsc(LocalDateTime data, User operatore);
	
	List<Prenotazione> findByOrarioGreaterThanAndClienteOrderByOrarioAsc(LocalDateTime data, User cliente);

	boolean existsByOrarioAndOperatore(LocalDateTime orario, User operatore);
}

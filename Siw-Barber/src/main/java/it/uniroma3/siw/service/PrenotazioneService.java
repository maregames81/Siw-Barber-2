package it.uniroma3.siw.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {

	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	
	
	@Transactional
	public Prenotazione findById(Long id) {
		return prenotazioneRepository.findById(id).get();
	}
	
	@Transactional
	public Iterable<Prenotazione> findAll() {
		return prenotazioneRepository.findAll();
	}

	@Transactional
	public void save(Prenotazione prenotazione) {
		this.prenotazioneRepository.save(prenotazione);		
	}
	
	
	@Transactional
	public List<Prenotazione> findByDataGreaterThan(LocalDateTime data){	
		return this.prenotazioneRepository.findByOrarioGreaterThanOrderByOrarioAsc(data);
	}
	
	@Transactional
	public List<Prenotazione> findByDataGreaterThanAndOperatore(LocalDateTime data, User operatore){	
		return this.prenotazioneRepository.findByOrarioGreaterThanAndOperatoreOrderByOrarioAsc(data, operatore);
	}
	
	
	@Transactional
	public List<Prenotazione> findByDataGreaterThanAndCliente(LocalDateTime data, User cliente){	
		return this.prenotazioneRepository.findByOrarioGreaterThanAndClienteOrderByOrarioAsc(data, cliente);
	}
	
	@Transactional
	public void delete(Long id) {
		this.prenotazioneRepository.deleteById(id);
	}

	@Transactional
	public boolean existByOrarioAndOperatore(LocalDateTime orario, User operatore) {
		// TODO Auto-generated method stub
		return this.prenotazioneRepository.existsByOrarioAndOperatore(orario,operatore);
	}
	
	
	
}

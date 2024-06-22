package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Prenotazione;
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
}

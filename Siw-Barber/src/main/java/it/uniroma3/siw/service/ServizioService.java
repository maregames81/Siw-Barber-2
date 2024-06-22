package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Servizio;
import it.uniroma3.siw.repository.ServizioRepository;



@Service
public class ServizioService {

	@Autowired
	private ServizioRepository servizioRepository;
	
	
	@Transactional
	public Servizio findById(Long id) {
		return servizioRepository.findById(id).get();
	}
	
	@Transactional
	public Iterable<Servizio> findAll() {
		return servizioRepository.findAll();
	}

	@Transactional
	public void save(Servizio servizio) {
		servizioRepository.save(servizio);		
	}
	
	@Transactional
	public Servizio findByNome(String nome) {
		return this.servizioRepository.findByNome(nome);
	}
}

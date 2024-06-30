package it.uniroma3.siw.service;

import java.util.Optional;

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

	@Transactional
	public void updatePrezzo(Long id, float prezzo) {
		Servizio servizio= this.findById(id);
		servizio.setPrezzo(prezzo);
		servizioRepository.save(servizio);

	}

	@Transactional
	public boolean existByNome(String nome) {
		
		return this.servizioRepository.existsByNome(nome);
	}
}

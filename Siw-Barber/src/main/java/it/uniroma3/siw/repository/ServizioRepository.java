package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;


import it.uniroma3.siw.model.Servizio;

public interface ServizioRepository extends CrudRepository<Servizio, Long> {

	public Servizio findByNome(String nome);

	public boolean existsByNome(String nome);
	
	
}

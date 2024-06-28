package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Prenotazione {

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private User cliente;
	
	@ManyToOne
	@JoinColumn(name = "operatore_id")
	private User operatore;
	
	
	@ManyToOne
	private Servizio servizio;
	
	@NotBlank
	private LocalDateTime orario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCliente() {
		return cliente;
	}

	public void setCliente(User cliente) {
		this.cliente = cliente;
	}

	public User getOperatore() {
		return operatore;
	}

	public void setOperatore(User operatore) {
		this.operatore = operatore;
	}
	
	
	
	
	

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}

	public LocalDateTime getOrario() {
		return orario;
	}

	public void setOrario(LocalDateTime orario) {
		this.orario = orario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, operatore, orario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prenotazione other = (Prenotazione) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(operatore, other.operatore)
				&& Objects.equals(orario, other.orario);
	}
	
	
	
	
}

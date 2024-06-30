package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@NotBlank
	private String email;
	
	@OneToMany(mappedBy = "operatore", cascade = CascadeType.ALL)
	private List<Prenotazione> prenotazioniOperatore;
	
	@OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL)
	private List<Prenotazione> prenotazioniCliente;
	
	@Lob
	@Column(columnDefinition = "TEXT")
	private String foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Prenotazione> getPrenotazioniOperatore() {
		return prenotazioniOperatore;
	}

	public void setPrenotazioniOperatore(List<Prenotazione> prenotazioniOperatore) {
		this.prenotazioniOperatore = prenotazioniOperatore;
	}

	public List<Prenotazione> getPrenotazioniCliente() {
		return prenotazioniCliente;
	}

	public void setPrenotazioniCliente(List<Prenotazione> prenotazioniCliente) {
		this.prenotazioniCliente = prenotazioniCliente;
	}
	
	
	
	
	

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, email, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(email, other.email)
				&& Objects.equals(nome, other.nome);
	}
	
	
	
}

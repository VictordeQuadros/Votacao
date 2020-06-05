package br.com.compasso.votacao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@Data
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private OpcaoDeVoto opcaoDeVoto;

	@OneToOne
	private Associado associado;

	public Voto(OpcaoDeVoto voto, Associado associado) {
		this.opcaoDeVoto = voto;
		this.associado = associado;
	}
}

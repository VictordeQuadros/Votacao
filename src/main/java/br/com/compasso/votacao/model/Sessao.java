package br.com.compasso.votacao.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.compasso.votacao.exception.TempoInvalidoException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sessao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime dataDeInicio;
	private LocalDateTime dataDeTermino;

	@OneToMany(fetch = FetchType.EAGER)
	private  Set<Voto> listaDeVotos = new HashSet<Voto>();

	private EstadoDeSessao estado = EstadoDeSessao.ABERTA;

	@OneToOne
	private  Pauta pauta;

	public Sessao(LocalDateTime dataDeTermino, LocalDateTime dataDeInicio, Pauta pauta) throws TempoInvalidoException {

		if (dataDeTermino.isBefore(dataDeInicio.plusMinutes(1))) {
			throw new TempoInvalidoException("Tempo mínimo de 1 minuto necessário");
		}
		this.dataDeTermino = dataDeTermino;
		this.dataDeInicio = dataDeInicio;
		this.pauta = pauta;
	}

	public void addVotos(Voto voto) {

		this.listaDeVotos.add(voto);
	}

}

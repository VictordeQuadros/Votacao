package br.com.compasso.votacao.builders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.compasso.votacao.exception.TempoInvalidoException;
import br.com.compasso.votacao.model.Associado;
import br.com.compasso.votacao.model.EstadoDeSessao;
import br.com.compasso.votacao.model.OpcaoDeVoto;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.model.Sessao;
import br.com.compasso.votacao.model.Voto;

public class SessaoBuilder {

	private Long id;
	private LocalDateTime dataDeInicio;
	private LocalDateTime dataDeTermino;
	private Pauta pauta;
	private EstadoDeSessao estado;
	private List<Voto> listaDeVotos;

	public SessaoBuilder() {
		this.dataDeInicio = LocalDateTime.now();
		this.dataDeTermino = LocalDateTime.now().plusMinutes(1);
		this.estado = EstadoDeSessao.ABERTA;
		this.listaDeVotos = new ArrayList<>();
	}

	public SessaoBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public SessaoBuilder terminoNaData(LocalDateTime data) {
		this.dataDeTermino = data;
		return this;
	}

	public SessaoBuilder voto(OpcaoDeVoto voto, Associado associado) {
		listaDeVotos.add(new Voto(voto, associado));
		return this;
	}

	public SessaoBuilder voto(Long id, OpcaoDeVoto voto, Associado associado) {
		listaDeVotos.add(new Voto(id, voto, associado));
		return this;
	}
	
	public SessaoBuilder paraA(Pauta pauta) {
		this.pauta = pauta;
		return this;
	}

	public SessaoBuilder encerra() {
		this.estado = EstadoDeSessao.FECHADA;
		return this;
	}

	public Sessao constroi() throws TempoInvalidoException {
		Sessao sessao = new Sessao(id, dataDeInicio, dataDeTermino, listaDeVotos, estado,pauta);

		return sessao;
	}
}

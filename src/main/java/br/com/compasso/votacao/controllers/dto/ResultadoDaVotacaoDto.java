package br.com.compasso.votacao.controllers.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.compasso.votacao.business.CalculadoraDeResultadosDaSessao;
import br.com.compasso.votacao.converter.dto.PautaParaPautaDto;
import br.com.compasso.votacao.model.EstadoDeSessao;
import br.com.compasso.votacao.model.Sessao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ResultadoDaVotacaoDto {

	private Long id = null;
	private LocalDateTime dataDeInicio = null;
	private LocalDateTime dataDeTermino = null;
	private Set<VotoDto> listaDeVotos = null;
	private EstadoDeSessao estado = null;
	private PautaDto pauta = null;
	private List<String> resultados = null;

	public  ResultadoDaVotacaoDto(){}
	

	public ResultadoDaVotacaoDto(Sessao sessao) {
		this.id = sessao.getId();
		this.dataDeInicio = sessao.getDataDeInicio();
		this.dataDeTermino = sessao.getDataDeTermino();
		this.listaDeVotos = new HashSet<VotoDto>();
		this.listaDeVotos.addAll(sessao.getListaDeVotos().stream().map(VotoDto::new).collect(Collectors.toList()));
		this.setResultados();
		this.estado = sessao.getEstado();
		this.pauta = new PautaParaPautaDto().converter(sessao.getPauta());

	}
	public ResultadoDaVotacaoDto build(Sessao sessao) {
		this.id = sessao.getId();
		this.dataDeInicio = sessao.getDataDeInicio();
		this.dataDeTermino = sessao.getDataDeTermino();
		this.listaDeVotos = new HashSet<VotoDto>();
		this.listaDeVotos.addAll(sessao.getListaDeVotos().stream().map(VotoDto::new).collect(Collectors.toList()));
		this.setResultados();
		this.estado = sessao.getEstado();
		this.pauta = new PautaParaPautaDto().converter(sessao.getPauta());
		return this;
	}

	private void setResultados() {
		this.resultados = new CalculadoraDeResultadosDaSessao().calcula(this.listaDeVotos);
	}
}

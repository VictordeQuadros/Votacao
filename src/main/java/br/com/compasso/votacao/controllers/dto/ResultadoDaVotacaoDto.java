package br.com.compasso.votacao.controllers.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@NoArgsConstructor
public class ResultadoDaVotacaoDto {

	private Long id;
	private LocalDateTime dataDeInicio;
	private LocalDateTime dataDeTermino;
	private List<VotoDto> listaDeVotos;
	private EstadoDeSessao estado;
	private PautaDto pauta;
	private List<String> resultados = new ArrayList<>();
	

	public ResultadoDaVotacaoDto(Sessao sessao) {
		this.id = sessao.getId();
		this.dataDeInicio = sessao.getDataDeInicio();
		this.dataDeTermino = sessao.getDataDeTermino();
		this.listaDeVotos = new ArrayList<>();
		this.listaDeVotos.addAll(sessao.getListaDeVotos().stream().map(VotoDto::new).collect(Collectors.toList()));
		this.setResultados();
		this.estado = sessao.getEstado();
		this.pauta = new PautaParaPautaDto().converter(sessao.getPauta());

	}
	public ResultadoDaVotacaoDto createBySessaoDto(SessaoDto sessaoDto) {
		this.id = sessaoDto.getId();
		this.dataDeInicio = sessaoDto.getDataDeInicio();
		this.dataDeTermino = sessaoDto.getDataDeTermino();
		this.listaDeVotos = sessaoDto.getListaDeVotos();
		this.setResultados();
		this.estado = sessaoDto.getEstado();
		this.pauta = sessaoDto.getPauta();
		return this;
	}

	private void setResultados() {
		this.resultados = new CalculadoraDeResultadosDaSessao().calcula(this.listaDeVotos);
	}
}

package br.com.compasso.votacao.controllers.dto;

import br.com.compasso.votacao.model.Pauta;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class PautaDto {

	private Long id;
	private String titulo;
	private String conteudo;

	public PautaDto(Pauta pauta) {
		this.id = pauta.getId();
		this.titulo = pauta.getTitulo();
		this.conteudo = pauta.getConteudo();
	}
}

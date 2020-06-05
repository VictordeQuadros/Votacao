package br.com.compasso.votacao.controllers.dto;

import br.com.compasso.votacao.converter.dto.AssociadoParaAssociadoDto;
import br.com.compasso.votacao.model.OpcaoDeVoto;
import br.com.compasso.votacao.model.Voto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class VotoDto {

	private Long id;
	private OpcaoDeVoto opcaoDeVoto;
	private AssociadoDto associadoDto;

	public VotoDto(Voto voto) {
		this.id = voto.getId();
		this.opcaoDeVoto = voto.getOpcaoDeVoto();
		this.associadoDto = new AssociadoParaAssociadoDto().converter(voto.getAssociado()) ;
	}
}

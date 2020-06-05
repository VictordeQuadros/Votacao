package br.com.compasso.votacao.controllers.dto;

import br.com.compasso.votacao.model.Associado;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class AssociadoDto {

	private Long id;
	private String nome;
	private String cpf;

	public AssociadoDto(Associado associado) {
		this.id = associado.getId();
		this.nome = associado.getNome();
		this.cpf = associado.getCpf();
	}
}

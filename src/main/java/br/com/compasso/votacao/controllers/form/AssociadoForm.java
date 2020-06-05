package br.com.compasso.votacao.controllers.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class AssociadoForm {

	@NotEmpty
	@NotNull
	private String nome;
	@NotEmpty
	@NotNull
	private String cpf;

}

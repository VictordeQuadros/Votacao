package br.com.compasso.votacao.controllers.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotaNaSessaoForm {

	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String voto;

	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String cpfDoAssociado;

}

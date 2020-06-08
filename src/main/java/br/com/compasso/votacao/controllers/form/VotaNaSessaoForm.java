package br.com.compasso.votacao.controllers.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@EqualsAndHashCode
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

package br.com.compasso.votacao.converter.form;

import br.com.compasso.votacao.controllers.form.AssociadoForm;
import br.com.compasso.votacao.model.Associado;

public class AssociadoFormParaAssociado implements ConversorDeFormParaEntidade<AssociadoForm, Associado> {

	@Override
	public Associado converter(AssociadoForm form) {
		Associado associado = new Associado(form.getNome(), form.getCpf());
		return associado;
	}

}

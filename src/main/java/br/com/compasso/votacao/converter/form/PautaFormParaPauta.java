package br.com.compasso.votacao.converter.form;

import br.com.compasso.votacao.controllers.form.PautaForm;
import br.com.compasso.votacao.model.Pauta;

public class PautaFormParaPauta implements ConversorDeFormParaEntidade<PautaForm, Pauta> {

	@Override
	public Pauta converter(PautaForm form) {
		Pauta pauta = new Pauta(form.getTitulo(), form.getConteudo());
		return pauta;
	}

}

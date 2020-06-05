package br.com.compasso.votacao.converter.form;

import br.com.compasso.votacao.exception.ConversaoInvalidaException;

public interface ConversorDeFormParaEntidade <form, entidade>{

	entidade converter(form form) throws ConversaoInvalidaException;

	
}

package br.com.compasso.votacao.converter.dto;

import java.util.List;

public interface ConversorDeEntidadeParaDto <entidade, dto>{

	dto converter(entidade entidadeParaConverter);
	List<dto> converterList(List<entidade> ListDaEntidadeParaConverter);
}

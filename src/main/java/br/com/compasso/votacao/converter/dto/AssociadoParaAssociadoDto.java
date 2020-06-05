package br.com.compasso.votacao.converter.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.compasso.votacao.controllers.dto.AssociadoDto;
import br.com.compasso.votacao.model.Associado;

public class AssociadoParaAssociadoDto implements ConversorDeEntidadeParaDto<Associado, AssociadoDto> {

	@Override
	public AssociadoDto converter(Associado entidadeParaConverter) {
		return new AssociadoDto(entidadeParaConverter);
	}

	@Override
	public List<AssociadoDto> converterList(List<Associado> listDaEntidadeParaConverter) {

		return listDaEntidadeParaConverter.stream().map(AssociadoDto::new).collect(Collectors.toList());
	}

}

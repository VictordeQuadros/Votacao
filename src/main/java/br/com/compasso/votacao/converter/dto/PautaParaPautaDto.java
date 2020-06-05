package br.com.compasso.votacao.converter.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.compasso.votacao.controllers.dto.PautaDto;
import br.com.compasso.votacao.model.Pauta;

public class PautaParaPautaDto implements ConversorDeEntidadeParaDto<Pauta, PautaDto> {

	@Override
	public PautaDto converter(Pauta entidadeParaConverter) {

		return new PautaDto(entidadeParaConverter);
	}

	@Override
	public List<PautaDto> converterList(List<Pauta> listDaEntidadeParaConverter) {

		return listDaEntidadeParaConverter.stream().map(PautaDto::new).collect(Collectors.toList());
	}

}

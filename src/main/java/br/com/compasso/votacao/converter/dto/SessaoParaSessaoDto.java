package br.com.compasso.votacao.converter.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.compasso.votacao.controllers.dto.SessaoDto;
import br.com.compasso.votacao.model.Sessao;
import org.springframework.stereotype.Component;

@Component
public class SessaoParaSessaoDto implements ConversorDeEntidadeParaDto<Sessao, SessaoDto> {

	@Override
	public SessaoDto converter(Sessao entidadeParaConverter) {
		return new SessaoDto(entidadeParaConverter);
	}

	@Override
	public List<SessaoDto> converterList(List<Sessao> listDaEntidadeParaConverter) {

		return listDaEntidadeParaConverter.stream().map(SessaoDto::new).collect(Collectors.toList());
	}

}

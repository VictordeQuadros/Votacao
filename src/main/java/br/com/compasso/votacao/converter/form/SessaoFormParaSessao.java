package br.com.compasso.votacao.converter.form;

import java.time.LocalDateTime;

import br.com.compasso.votacao.builders.SessaoBuilder;
import br.com.compasso.votacao.controllers.form.SessaoForm;
import br.com.compasso.votacao.exception.ConversaoInvalidaException;
import br.com.compasso.votacao.exception.PautaInexistenteException;
import br.com.compasso.votacao.exception.TempoInvalidoException;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.model.Sessao;
import br.com.compasso.votacao.repository.PautaRepository;
import br.com.compasso.votacao.service.PautaService;
import org.springframework.stereotype.Component;

@Component
public class SessaoFormParaSessao implements ConversorDeFormParaEntidade<SessaoForm, Sessao> {

	public Sessao converter(SessaoForm form, PautaRepository pautaRepository)
			throws TempoInvalidoException, PautaInexistenteException {
		SessaoBuilder builder = new SessaoBuilder();
		if (form.getDuracaoEmMinutos() != null && !form.getDuracaoEmMinutos().equals("")) {
			builder.terminoNaData(LocalDateTime.now().plusMinutes(Long.parseLong(form.getDuracaoEmMinutos())));
		}
		Pauta pautaParaSessao = new PautaService(pautaRepository).getPauta(form.getTituloDaPauta());

		return builder.paraA(pautaParaSessao).constroi();
	}

	@Override
	public Sessao converter(SessaoForm form) throws ConversaoInvalidaException {
		throw new ConversaoInvalidaException("Foi realizada uma conversão de maneira indevida");
	}

}

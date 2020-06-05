package br.com.compasso.votacao.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.compasso.votacao.builders.SessaoBuilder;
import br.com.compasso.votacao.exception.TempoInvalidoException;

class SessaoTest {

	@Test
	public void testaAberturaDeSessaoSemHorarioMinimo() throws TempoInvalidoException {

		Assertions.assertThrows(TempoInvalidoException.class, () -> {
			new Sessao(LocalDateTime.now().minusMinutes(5), LocalDateTime.now(),
					new Pauta("Nova Pauta", "essa pauta está funcionando?"));
		});

	}

	@Test
	public void testaAberturaDeSessaoSemTerminoDefinido() throws TempoInvalidoException {

		SessaoBuilder builder = new SessaoBuilder();

		Sessao sessao = builder.paraA(new Pauta("teste", "Pauta Teste"))
				.voto(OpcaoDeVoto.SIM, new Associado("João", "22222222222"))
				.voto(OpcaoDeVoto.NAO, new Associado("Ana", "33333333333"))
				.voto(OpcaoDeVoto.NAO, new Associado("Maria", "44444444444")).constroi();

		assertTrue(sessao.getDataDeInicio().isBefore(sessao.getDataDeTermino()));

	}
	
	
}

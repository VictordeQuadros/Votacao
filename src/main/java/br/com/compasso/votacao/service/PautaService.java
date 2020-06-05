package br.com.compasso.votacao.service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.compasso.votacao.exception.PautaInexistenteException;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.repository.PautaRepository;

@Service
public class PautaService {

	private final PautaRepository pautaRepository;

	public PautaService(PautaRepository pautaRepository) {
		this.pautaRepository = pautaRepository;
	}

	public Pauta getPauta(String titulo) throws PautaInexistenteException {

		List<Pauta> pauta = pautaRepository.findByTitulo(titulo);

		if (pauta.isEmpty()){
			throw new PautaInexistenteException("Pauta Inexistente");
		}
			return pauta.get(0);
	}
}

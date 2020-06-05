package br.com.compasso.votacao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.compasso.votacao.exception.AssociadoInexistenteException;
import br.com.compasso.votacao.model.Associado;
import br.com.compasso.votacao.repository.AssociadoRepository;

@Service
public class AssociadoService {

	private final  AssociadoRepository associadoRepository;

	public AssociadoService(AssociadoRepository associadoRepository) {
		this.associadoRepository = associadoRepository;
	}

	public Associado findByCPF(String cpf)
			throws AssociadoInexistenteException {

			List<Associado> associado = associadoRepository.findByCpf(cpf);

			if (associado.isEmpty()){
				throw new AssociadoInexistenteException("Associado não existe com esse CPF");
			}
			return associado.get(0);
	}

}

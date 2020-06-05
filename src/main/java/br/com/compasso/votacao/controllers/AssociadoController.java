package br.com.compasso.votacao.controllers;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.votacao.controllers.dto.AssociadoDto;
import br.com.compasso.votacao.controllers.form.AssociadoForm;
import br.com.compasso.votacao.converter.dto.AssociadoParaAssociadoDto;
import br.com.compasso.votacao.converter.form.AssociadoFormParaAssociado;
import br.com.compasso.votacao.model.Associado;
import br.com.compasso.votacao.repository.AssociadoRepository;

@RestController
@RequestMapping("/associados")
public class AssociadoController {

	@Autowired
	AssociadoRepository associadoRepository;
	
	@GetMapping
	public List<AssociadoDto> lista(String cpf) {

		if (cpf == null) {
			List<Associado> associado = associadoRepository.findAll();
			return new AssociadoParaAssociadoDto().converterList(associado);
		} else {
			List<Associado> associado = associadoRepository.findByCpf(cpf);
			return new AssociadoParaAssociadoDto().converterList(associado);
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<AssociadoDto> cadastrar(@RequestBody @Valid AssociadoForm form, UriComponentsBuilder uriBuilder) {

		Associado associado = new AssociadoFormParaAssociado().converter(form);
		associadoRepository.save(associado);
		URI uri = uriBuilder.path("/pauta/{id}").buildAndExpand(associado.getId()).toUri();

		return ResponseEntity.created(uri).body(new AssociadoDto(associado));
	}
}

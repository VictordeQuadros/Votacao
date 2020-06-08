package br.com.compasso.votacao.controllers;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.compasso.votacao.controllers.dto.PautaDto;
import br.com.compasso.votacao.model.Pauta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

	@Autowired
	AssociadoParaAssociadoDto associadoParaAssociadoDto;

	@GetMapping
	public ResponseEntity<List<AssociadoDto>> lista() {
		List<Associado> associado = associadoRepository.findAll();
		List<AssociadoDto> associadoDtos = associadoParaAssociadoDto.converterList(associado);
		return ResponseEntity.ok(associadoDtos);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<List<AssociadoDto>> buscaAssocidoDoCpf(@PathVariable String cpf) {
		List<Associado> associado = associadoRepository.findByCpf(cpf);
		List<AssociadoDto> associadoDtos = associadoParaAssociadoDto.converterList(associado);
		return ResponseEntity.ok(associadoDtos);
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

package br.com.compasso.votacao.controllers;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.compasso.votacao.controllers.dto.SessaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.votacao.controllers.dto.PautaDto;
import br.com.compasso.votacao.controllers.form.PautaForm;
import br.com.compasso.votacao.converter.dto.PautaParaPautaDto;
import br.com.compasso.votacao.converter.form.PautaFormParaPauta;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.repository.PautaRepository;

@RestController
@RequestMapping("/pautas")
public class PautasController {

	@Autowired
	private PautaRepository pautaRepository;
	@Autowired
	private PautaParaPautaDto pautaParaPautaDto;

	@GetMapping
	public ResponseEntity<List<PautaDto>> lista() {
		List<Pauta> pautas = pautaRepository.findAll();
		List<PautaDto> pautaDtos = pautaParaPautaDto.converterList(pautas);
		return ResponseEntity.ok(pautaDtos);
	}

	@GetMapping("/{titulo}")
	public ResponseEntity<List<PautaDto>> buscaPautaDoTitulo(@PathVariable String titulo) {
		List<Pauta> pauta = pautaRepository.findByTitulo(titulo);
		List<PautaDto> pautaDto = pautaParaPautaDto.converterList(pauta);
		return ResponseEntity.ok(pautaDto);
	}


	@PostMapping
	@Transactional
	public ResponseEntity<PautaDto> cadastrar(@RequestBody @Valid PautaForm form, UriComponentsBuilder uriBuilder) {

		Pauta pauta = new PautaFormParaPauta().converter(form);
		pautaRepository.save(pauta);
		URI uri = uriBuilder.path("/pauta/{id}").buildAndExpand(pauta.getId()).toUri();

		return ResponseEntity.created(uri).body(new PautaDto(pauta));
	}
}

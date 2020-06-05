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

	@GetMapping
	public ResponseEntity<List<PautaDto>> lista(String titulo) {

		List<PautaDto> pautaDtos;
		if (titulo == null) {
			List<Pauta> pauta = pautaRepository.findAll();
			pautaDtos = new PautaParaPautaDto().converterList(pauta);
		} else {
			List<Pauta> pauta = pautaRepository.findByTitulo(titulo);
			pautaDtos = new PautaParaPautaDto().converterList(pauta);
		}

		if(pautaDtos.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pautaDtos);
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

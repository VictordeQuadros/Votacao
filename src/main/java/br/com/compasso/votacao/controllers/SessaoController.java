package br.com.compasso.votacao.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.votacao.controllers.dto.ResultadoDaVotacaoDto;
import br.com.compasso.votacao.controllers.dto.SessaoDto;
import br.com.compasso.votacao.controllers.form.SessaoForm;
import br.com.compasso.votacao.controllers.form.VotaNaSessaoForm;
import br.com.compasso.votacao.exception.AssociadoInexistenteException;
import br.com.compasso.votacao.exception.AssociadoJaVotouException;
import br.com.compasso.votacao.exception.PautaInexistenteException;
import br.com.compasso.votacao.exception.TempoInvalidoException;
import br.com.compasso.votacao.model.Sessao;
import br.com.compasso.votacao.service.SessaoService;

@RestController
@RequestMapping("/sessao")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @GetMapping
    public ResponseEntity<List<SessaoDto>> lista() {
        List<SessaoDto> sessaoDtos = sessaoService.listaAsSessoes();
        return ResponseEntity.ok(sessaoDtos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<SessaoDto> cadastrar(@RequestBody @Valid SessaoForm form, UriComponentsBuilder uriBuilder)
            throws TempoInvalidoException, PautaInexistenteException {

        SessaoDto sessaoDto = sessaoService.salvaPeloForm(form);
        URI uri = uriBuilder.path("/sessao/{id}").buildAndExpand(sessaoDto.getId()).toUri();

        return ResponseEntity.created(uri).body(sessaoDto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SessaoDto> votacao(@PathVariable Long id, @RequestBody @Valid VotaNaSessaoForm form)
            throws AssociadoInexistenteException, AssociadoJaVotouException {

        SessaoDto sessaoDto = sessaoService.vota(id, form);

        return ResponseEntity.ok(sessaoDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoDaVotacaoDto> resultados(@PathVariable Long id) {

        ResultadoDaVotacaoDto resultadoDaVotacaoDto = sessaoService.resultados(id);

        return ResponseEntity.ok(resultadoDaVotacaoDto);

    }

}

package br.com.compasso.votacao.service;

import java.time.LocalDateTime;
import java.util.*;

import br.com.compasso.votacao.controllers.dto.ResultadoDaVotacaoDto;
import br.com.compasso.votacao.controllers.dto.SessaoDto;
import br.com.compasso.votacao.controllers.form.SessaoForm;
import br.com.compasso.votacao.converter.dto.SessaoParaSessaoDto;
import br.com.compasso.votacao.converter.form.SessaoFormParaSessao;
import br.com.compasso.votacao.exception.*;
import br.com.compasso.votacao.repository.PautaRepository;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.stereotype.Service;

import br.com.compasso.votacao.controllers.form.VotaNaSessaoForm;
import br.com.compasso.votacao.model.EstadoDeSessao;
import br.com.compasso.votacao.model.OpcaoDeVoto;
import br.com.compasso.votacao.model.Sessao;
import br.com.compasso.votacao.model.Voto;
import br.com.compasso.votacao.repository.SessaoRepository;
import br.com.compasso.votacao.repository.VotoRepository;

@Service
public class SessaoService {

    private final AssociadoService associadoService;
    private final SessaoRepository sessaoRepository;
    private final VotoRepository votoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoParaSessaoDto sessaoParaSessaoDto;
    private final SessaoFormParaSessao sessaoFormParaSessao;
    private final ResultadoDaVotacaoDto resultadoDaVotacaoDto;

    public SessaoService(AssociadoService associadoService, SessaoRepository sessaoRepository, VotoRepository votoRepository,
                         PautaRepository pautaRepository, SessaoParaSessaoDto sessaoParaSessaoDto,
                         SessaoFormParaSessao sessaoFormParaSessao, ResultadoDaVotacaoDto resultadoDaVotacaoDto) {
        this.associadoService = associadoService;
        this.sessaoRepository = sessaoRepository;
        this.votoRepository = votoRepository;
        this.pautaRepository = pautaRepository;
        this.sessaoParaSessaoDto = sessaoParaSessaoDto;
        this.sessaoFormParaSessao = sessaoFormParaSessao;
        this.resultadoDaVotacaoDto = resultadoDaVotacaoDto;
    }

    public SessaoDto vota(Long id, VotaNaSessaoForm form) throws AssociadoInexistenteException, AssociadoJaVotouException {

        Optional<Sessao> optional = sessaoRepository.findById(id);
        Sessao sessao = optional.get();
        Set<Voto> votos = sessao.getListaDeVotos();
        procuraPorCpfSeAPessoaJaVotou(form, votos);
        verificaSeASessaoEstaAbertaEVota(form, sessao, id);
        SessaoDto sessaoDto = sessaoParaSessaoDto.converter(sessao);

        return sessaoDto;
    }

    private void verificaSeASessaoEstaAbertaEVota(VotaNaSessaoForm form, Sessao sessao, Long id) throws AssociadoInexistenteException {
        if (sessao.getEstado() == EstadoDeSessao.ABERTA) {
            if (form.getVoto().toUpperCase().compareTo("SIM") == 0) {
                realizaOVoto(form, sessao, OpcaoDeVoto.SIM);
            } else {
                realizaOVoto(form, sessao, OpcaoDeVoto.NAO);
            }
        } else {
            throw new SessaoEncerradaException(id);
        }
    }

    private void procuraPorCpfSeAPessoaJaVotou(VotaNaSessaoForm form, Set<Voto> votos) throws AssociadoJaVotouException {

        if (votos
                .stream()
                .anyMatch(voto -> voto.getAssociado().getCpf().equalsIgnoreCase(form.getCpfDoAssociado()))) {
            throw new AssociadoJaVotouException("Associado do CPF: " + form.getCpfDoAssociado() + " já votou.");
        }
    }

    private void realizaOVoto(VotaNaSessaoForm form, Sessao sessao, OpcaoDeVoto sim) throws AssociadoInexistenteException {

        Voto voto = new Voto(sim, associadoService.findByCPF(form.getCpfDoAssociado()));
        votoRepository.save(voto);
        sessao.addVotos(voto);
    }

    //testado
    public Sessao findById(Long id) {

        return sessaoRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));

    }

    private void fechaSessao(Sessao sessao) {
        if (sessao.getDataDeTermino().isBefore(LocalDateTime.now())) {
            sessao.setEstado(EstadoDeSessao.FECHADA);
            sessaoRepository.flush();
        }
    }

    // testado
    public Sessao salvaPeloForm(SessaoForm form) throws TempoInvalidoException, PautaInexistenteException {

        Sessao sessao = sessaoFormParaSessao.converter(form, pautaRepository);
        sessaoRepository.save(sessao);
        return sessao;
    }

    // testado
    public List<SessaoDto> listaAsSessoes() {
        List<Sessao> sessoes = sessaoRepository.findAll();
        sessoes.parallelStream().forEach(this::fechaSessao);
        return sessaoParaSessaoDto.converterList(sessoes);
    }

}

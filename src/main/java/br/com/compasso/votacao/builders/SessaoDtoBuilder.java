package br.com.compasso.votacao.builders;

import br.com.compasso.votacao.controllers.dto.AssociadoDto;
import br.com.compasso.votacao.controllers.dto.PautaDto;
import br.com.compasso.votacao.controllers.dto.SessaoDto;
import br.com.compasso.votacao.controllers.dto.VotoDto;
import br.com.compasso.votacao.exception.TempoInvalidoException;
import br.com.compasso.votacao.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SessaoDtoBuilder {

    private Long id;
    private LocalDateTime dataDeInicio;
    private LocalDateTime dataDeTermino;
    private PautaDto pautaDto;
    private EstadoDeSessao estado;
    private List<VotoDto> listaDeVotosDto;



    public SessaoDtoBuilder() {
        this.dataDeInicio = LocalDateTime.now();
        this.dataDeTermino = LocalDateTime.now().plusMinutes(1);
        this.estado = EstadoDeSessao.ABERTA;
        this.listaDeVotosDto = new ArrayList<>();
    }

    public SessaoDtoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public SessaoDtoBuilder terminoNaData(LocalDateTime data) {
        this.dataDeTermino = data;
        return this;
    }

    public SessaoDtoBuilder voto(Long id,OpcaoDeVoto voto, AssociadoDto associado) {
        listaDeVotosDto.add(new VotoDto(id,voto, associado));
        return this;
    }

    public SessaoDtoBuilder paraA(PautaDto pautaDto) {
        this.pautaDto = pautaDto;
        return this;
    }

    public SessaoDtoBuilder encerra() {
        this.estado = EstadoDeSessao.FECHADA;
        return this;
    }

    public SessaoDto constroi() throws TempoInvalidoException {
        SessaoDto sessaoDto =
                new SessaoDto(id, dataDeInicio, dataDeTermino, listaDeVotosDto, estado, pautaDto);

        return sessaoDto;
    }
}

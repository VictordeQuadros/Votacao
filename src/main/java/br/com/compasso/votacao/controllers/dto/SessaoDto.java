package br.com.compasso.votacao.controllers.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.compasso.votacao.converter.dto.PautaParaPautaDto;
import br.com.compasso.votacao.model.EstadoDeSessao;
import br.com.compasso.votacao.model.Sessao;
import br.com.compasso.votacao.model.Voto;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class SessaoDto {

    private Long id;
    private LocalDateTime dataDeInicio;
    private LocalDateTime dataDeTermino;
    private List<VotoDto> listaDeVotos;
    private EstadoDeSessao estado;
    private PautaDto pauta;

    public SessaoDto() {
        this.listaDeVotos = new ArrayList<>();
    }

    public SessaoDto(Sessao sessao) {
        this.id = sessao.getId();
        this.dataDeInicio = sessao.getDataDeInicio();
        this.dataDeTermino = sessao.getDataDeTermino();
        this.listaDeVotos = new ArrayList<>();
        this.listaDeVotos.addAll(sessao.getListaDeVotos().stream().map(VotoDto::new).collect(Collectors.toList()));
        this.estado = sessao.getEstado();
        this.pauta = new PautaParaPautaDto().converter(sessao.getPauta());

    }

    public void addVotos(VotoDto voto) {

        this.listaDeVotos.add(voto);
    }

}

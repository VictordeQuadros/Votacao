package br.com.compasso.votacao.controllers;

import br.com.compasso.votacao.builders.SessaoBuilder;
import br.com.compasso.votacao.builders.SessaoDtoBuilder;
import br.com.compasso.votacao.controllers.dto.*;
import br.com.compasso.votacao.controllers.form.VotaNaSessaoForm;
import br.com.compasso.votacao.exception.TempoInvalidoException;
import br.com.compasso.votacao.model.Associado;
import br.com.compasso.votacao.model.OpcaoDeVoto;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.model.Sessao;
import br.com.compasso.votacao.service.SessaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.net.ssl.SSLEngineResult;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessaoController.class)
class SessaoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper;

    @MockBean
    private SessaoService sessaoService;

    private static final Long TEST_ID = 1L;

    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void testaListaSucesso() throws Exception {
        SessaoDto sessaoDto = generateSessaoDto();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSS");

        when(sessaoService.listaAsSessoes()).thenReturn(Arrays.asList(sessaoDto));


        mockMvc.perform(get("/sessao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sessaoDto.getId()))
                .andExpect(jsonPath("$[0].dataDeInicio").value(sessaoDto.getDataDeInicio().format(dateTimeFormatter)))
                .andExpect(jsonPath("$[0].dataDeTermino").value(sessaoDto.getDataDeTermino().format(dateTimeFormatter)))
                .andExpect(jsonPath("$[0].listaDeVotos[0]").value(sessaoDto.getListaDeVotos().get(0)))
                .andExpect(jsonPath("$[0].estado").value(sessaoDto.getEstado().getDescricao()))
                .andExpect(jsonPath("$[0].pauta.id").value(sessaoDto.getPauta().getId()))
                .andExpect(jsonPath("$[0].pauta.titulo").value(sessaoDto.getPauta().getTitulo()))
                .andExpect(jsonPath("$[0].pauta.conteudo").value(sessaoDto.getPauta().getConteudo()));

    }

    @Test
    public void testaListaResultadoSucesso() throws Exception {
        ResultadoDaVotacaoDto resultado = generateResultadoDaVotacaoDto();

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSS");

        when(sessaoService.resultados(eq(TEST_ID))).thenReturn(resultado);

        mockMvc.perform(get((String.format("%s/%s", "/sessao", TEST_ID))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resultado.getId()))
                .andExpect(jsonPath("$.dataDeInicio").value(resultado.getDataDeInicio().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.dataDeTermino").value(resultado.getDataDeTermino().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.listaDeVotos[0]").value(resultado.getListaDeVotos().get(0)))
                .andExpect(jsonPath("$.estado").value(resultado.getEstado().getDescricao()))
                .andExpect(jsonPath("$.pauta.id").value(resultado.getPauta().getId()))
                .andExpect(jsonPath("$.pauta.titulo").value(resultado.getPauta().getTitulo()))
                .andExpect(jsonPath("$.pauta.conteudo").value(resultado.getPauta().getConteudo()))
                .andExpect(jsonPath("$.resultados[0]").value(resultado.getResultados().get(0)))
                .andExpect(jsonPath("$.resultados[1]").value(resultado.getResultados().get(1)))
                .andExpect(jsonPath("$.resultados[2]").value(resultado.getResultados().get(2)))
                .andExpect(jsonPath("$.resultados[3]").value(resultado.getResultados().get(3)));

    }

    @Test
    public void votacaoTestSucesso() throws Exception {
        SessaoDto sessaoDto = generateSessaoDto();
        VotaNaSessaoForm votaNaSessaoForm = new VotaNaSessaoForm("SIM", "cpf_test");

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSSSSS");

        when(sessaoService.vota(eq(TEST_ID), eq(votaNaSessaoForm))).thenReturn(sessaoDto);

        mockMvc.perform(put((String.format("%s/%s", "/sessao", TEST_ID)))
                .content(mapper.writeValueAsString(votaNaSessaoForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sessaoDto.getId()))
                .andExpect(jsonPath("$.dataDeInicio").value(sessaoDto.getDataDeInicio().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.dataDeTermino").value(sessaoDto.getDataDeTermino().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.listaDeVotos[0]").value(sessaoDto.getListaDeVotos().get(0)))
                .andExpect(jsonPath("$.estado").value(sessaoDto.getEstado().getDescricao()))
                .andExpect(jsonPath("$.pauta.id").value(sessaoDto.getPauta().getId()))
                .andExpect(jsonPath("$.pauta.titulo").value(sessaoDto.getPauta().getTitulo()))
                .andExpect(jsonPath("$.pauta.conteudo").value(sessaoDto.getPauta().getConteudo()));

    }

    @Test
    public void cadastroTestSucesso() {

        
    }


    public static AssociadoDto generateAssociadoDto(){
        return new AssociadoDto(TEST_ID, "nome_test","cpf_test");
    }

    public static PautaDto generatePautaDto(){
        return new PautaDto(TEST_ID, "test_titulo", "test_conteudo");
    }

    public static SessaoDto generateSessaoDto() throws TempoInvalidoException {
        return new SessaoDtoBuilder()
                .id(TEST_ID)
                .paraA(generatePautaDto())
                .voto(TEST_ID, OpcaoDeVoto.SIM, generateAssociadoDto())
                .constroi();
    }

    public static ResultadoDaVotacaoDto generateResultadoDaVotacaoDto() throws TempoInvalidoException {
        return new ResultadoDaVotacaoDto(generateSessao());
    }

    public static Associado generateAssociado(){
        return new Associado(TEST_ID, "nome_test","cpf_test");
    }

    public static Pauta generatePauta(){
        return new Pauta(TEST_ID, "test_titulo", "test_conteudo");
    }

     public static Sessao generateSessao() throws TempoInvalidoException {
        return new SessaoBuilder()
                    .id(TEST_ID)
                    .voto(TEST_ID, OpcaoDeVoto.SIM, generateAssociado())
                    .paraA(generatePauta())
                    .constroi();
     }


}
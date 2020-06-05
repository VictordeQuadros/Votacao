package br.com.compasso.votacao.controllers;

import br.com.compasso.votacao.builders.SessaoDtoBuilder;
import br.com.compasso.votacao.controllers.dto.AssociadoDto;
import br.com.compasso.votacao.controllers.dto.PautaDto;
import br.com.compasso.votacao.controllers.dto.SessaoDto;
import br.com.compasso.votacao.controllers.dto.VotoDto;
import br.com.compasso.votacao.exception.TempoInvalidoException;
import br.com.compasso.votacao.model.OpcaoDeVoto;
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
    public void testaListaResultadoSucesso(){
        

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


}
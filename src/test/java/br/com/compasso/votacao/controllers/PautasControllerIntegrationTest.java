package br.com.compasso.votacao.controllers;

import br.com.compasso.votacao.controllers.dto.PautaDto;
import br.com.compasso.votacao.converter.dto.PautaParaPautaDto;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.repository.PautaRepository;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PautasController.class)
class PautasControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper;

    @MockBean
    private PautaRepository pautaRepository;

    @MockBean
    private PautaParaPautaDto pautaParaPautaDto;


    private static final Long TEST_ID = 1L;
    private static final String TEST_TITULO = "test_titulo";


    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void testaListaSucesso() throws Exception {
        List<Pauta> pautas = Arrays.asList(generatePauta());
        List<PautaDto> pautaDtos = Arrays.asList(generatePautaDto());

        when(pautaRepository.findAll()).thenReturn(pautas);
        when(pautaParaPautaDto.converterList(eq(pautas))).thenReturn(pautaDtos);


        mockMvc.perform(get("/pautas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(pautaDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].titulo").value(pautaDtos.get(0).getTitulo()))
                .andExpect(jsonPath("$[0].conteudo").value(pautaDtos.get(0).getConteudo()));
    }

    @Test
    public void testaBuscaPautaDoTituloSucesso() throws Exception {
        List<Pauta> pautas = Arrays.asList(generatePauta());
        List<PautaDto> pautaDtos = Arrays.asList(generatePautaDto());

        when(pautaRepository.findByTitulo(eq(TEST_TITULO))).thenReturn(pautas);
        when(pautaParaPautaDto.converterList(eq(pautas))).thenReturn(pautaDtos);


        mockMvc.perform(get((String.format("%s/%s", "/pautas", TEST_TITULO))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(pautaDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].titulo").value(pautaDtos.get(0).getTitulo()))
                .andExpect(jsonPath("$[0].conteudo").value(pautaDtos.get(0).getConteudo()));
    }

    public static Pauta generatePauta(){
        return new Pauta(TEST_ID, TEST_TITULO, "test_conteudo");
    }

    public static PautaDto generatePautaDto(){
        return new PautaDto(TEST_ID, TEST_TITULO, "test_conteudo");
    }

}
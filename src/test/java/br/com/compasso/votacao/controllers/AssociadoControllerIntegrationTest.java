package br.com.compasso.votacao.controllers;

import br.com.compasso.votacao.controllers.dto.AssociadoDto;
import br.com.compasso.votacao.controllers.dto.PautaDto;
import br.com.compasso.votacao.converter.dto.AssociadoParaAssociadoDto;
import br.com.compasso.votacao.model.Associado;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.repository.AssociadoRepository;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssociadoController.class)
class AssociadoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper;

    @MockBean
    private AssociadoRepository associadoRepository;

    @MockBean
    private AssociadoParaAssociadoDto associadoParaAssociadoDto;


    private static final Long TEST_ID = 1L;
    private static final String TEST_CPF = "cpf_test";


    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void testaListaSucesso() throws Exception {
        List<Associado> associados = Arrays.asList(generateAssociado());
        List<AssociadoDto> associadoDtos = Arrays.asList(generateAssociadoDto());

        when(associadoRepository.findAll()).thenReturn(associados);
        when(associadoParaAssociadoDto.converterList(eq(associados))).thenReturn(associadoDtos);


        mockMvc.perform(get("/associados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(associadoDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].nome").value(associadoDtos.get(0).getNome()))
                .andExpect(jsonPath("$[0].cpf").value(associadoDtos.get(0).getCpf()));
    }


    @Test
    public void testaBuscaPautaDoTituloSucesso() throws Exception {
        List<Associado> associados = Arrays.asList(generateAssociado());
        List<AssociadoDto> associadoDtos = Arrays.asList(generateAssociadoDto());

        when(associadoRepository.findByCpf(eq(TEST_CPF))).thenReturn(associados);
        when(associadoParaAssociadoDto.converterList(eq(associados))).thenReturn(associadoDtos);


        mockMvc.perform(get((String.format("%s/%s", "/associados", TEST_CPF))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(associadoDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].nome").value(associadoDtos.get(0).getNome()))
                .andExpect(jsonPath("$[0].cpf").value(associadoDtos.get(0).getCpf()));
    }


    public static Associado generateAssociado(){
        return new Associado(TEST_ID, "nome_test",TEST_CPF);
    }

    public static AssociadoDto generateAssociadoDto(){
        return new AssociadoDto(TEST_ID, "nome_test",TEST_CPF);
    }
}
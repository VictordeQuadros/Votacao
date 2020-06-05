package br.com.compasso.votacao.service;

import br.com.compasso.votacao.exception.PautaInexistenteException;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.repository.PautaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    private static Pauta pautaMock;

    private static final Long TEST_ID = 1L;
    private static final String TEST_TITULO = "testTitulo";
    private static final String TEST_CONTEUDO = "testConteudo";

    @BeforeAll
    public static void setUp() {
        pautaMock = generatePauta();
    }

    @Test
    public void getPautaSucesso() throws PautaInexistenteException {
        when(pautaRepository.findByTitulo(eq(TEST_TITULO))).thenReturn(Arrays.asList(pautaMock));
        Pauta novaPauta = pautaService.getPauta(TEST_TITULO);
        assertEquals(pautaMock, novaPauta);
        verify(pautaRepository).findByTitulo(eq(TEST_TITULO));

    }

    @Test
    public void getPautaErro(){
        assertThrows(PautaInexistenteException.class, () ->
                        pautaService.getPauta(TEST_TITULO),
                "Pauta Inexistente");
    }

    private static Pauta generatePauta(){
        return new Pauta(TEST_ID, TEST_TITULO, TEST_CONTEUDO);
    }
}
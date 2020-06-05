package br.com.compasso.votacao.service;

import br.com.compasso.votacao.exception.AssociadoInexistenteException;
import br.com.compasso.votacao.model.Associado;
import br.com.compasso.votacao.model.Pauta;
import br.com.compasso.votacao.repository.AssociadoRepository;
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
public class AssociadoServiceTest {

    @Mock
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private AssociadoService associadoService;

    private static Associado associadoMock;

    @BeforeAll
    public static void setUp(){
        associadoMock = generateAssociado();
    }

    private static final Long TEST_ID = 1L;
    private static final String TEST_NOME = "testNome";
    private static final String TEST_CPF = "testCpf";

    @Test
    public void getAssociadoSucesso() throws AssociadoInexistenteException {
        when(associadoRepository.findByCpf(eq(TEST_CPF))).thenReturn(Arrays.asList(associadoMock));
        Associado novoAssociado = associadoService.findByCPF(TEST_CPF);
        assertEquals(associadoMock,novoAssociado);
        verify(associadoRepository).findByCpf(eq(TEST_CPF));

    }

    @Test
    public void getAssociadoErro(){
        assertThrows(AssociadoInexistenteException.class, () ->
                associadoService.findByCPF(TEST_CPF),
                "Associado não existe com esse CPF");
    }

    private static Associado generateAssociado(){
        return new Associado(TEST_ID, TEST_NOME, TEST_CPF);
    }
}
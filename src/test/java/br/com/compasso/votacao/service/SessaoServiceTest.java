package br.com.compasso.votacao.service;

import br.com.compasso.votacao.builders.SessaoBuilder;
import br.com.compasso.votacao.controllers.dto.ResultadoDaVotacaoDto;
import br.com.compasso.votacao.controllers.dto.SessaoDto;
import br.com.compasso.votacao.controllers.form.SessaoForm;
import br.com.compasso.votacao.controllers.form.VotaNaSessaoForm;
import br.com.compasso.votacao.converter.dto.SessaoParaSessaoDto;
import br.com.compasso.votacao.converter.form.SessaoFormParaSessao;
import br.com.compasso.votacao.exception.*;
import br.com.compasso.votacao.model.*;
import br.com.compasso.votacao.repository.PautaRepository;
import br.com.compasso.votacao.repository.SessaoRepository;
import br.com.compasso.votacao.repository.VotoRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SessaoServiceTest {


    private static AssociadoService associadoService;

    private static SessaoRepository sessaoRepository;

    private static VotoRepository votoRepository;

    private static PautaRepository pautaRepository;

    private static SessaoParaSessaoDto sessaoParaSessaoDto;

    private static SessaoFormParaSessao sessaoFormParaSessao;

    private static ResultadoDaVotacaoDto resultadoDaVotacaoDto;

    private static SessaoService sessaoService;

    private static Associado associadoMock;
    private static Pauta pautaMock;
    private static Sessao sessaoAbertaMock;
    private static Sessao sessaoEncerradaMock;

    private static final Long TEST_ID = 1L;
    private static final String TEST_TITULO = "testPautaTitulo";
    private static final String TEST_CONTEUDO = "testPautaConteudo";
    private static final String TEST_NOME = "testAssociadoNome";
    private static final String TEST_CPF = "testAssociadoCpf";
    private static final OpcaoDeVoto TEST_VOTO_SIM = OpcaoDeVoto.SIM;
    private static final OpcaoDeVoto TEST_VOTO_NAO = OpcaoDeVoto.NAO;

    @BeforeAll
    public static void setUp() throws TempoInvalidoException {
        associadoMock = generateAssociado();
        pautaMock = generatePauta();
        sessaoAbertaMock = generateSessao();
        sessaoEncerradaMock = generateSessaoEncerrada();
        associadoService = Mockito.mock(AssociadoService.class);
        sessaoRepository = Mockito.mock(SessaoRepository.class);
        votoRepository = Mockito.mock(VotoRepository.class);
        pautaRepository = Mockito.mock(PautaRepository.class);
        sessaoParaSessaoDto = Mockito.mock(SessaoParaSessaoDto.class);
        sessaoFormParaSessao = Mockito.mock(SessaoFormParaSessao.class);
        resultadoDaVotacaoDto = Mockito.mock(ResultadoDaVotacaoDto.class);
        sessaoService = new SessaoService(associadoService, sessaoRepository, votoRepository, pautaRepository,
                sessaoParaSessaoDto, sessaoFormParaSessao, resultadoDaVotacaoDto);
    }

    @AfterEach
    public void reset() {
        Mockito.reset(associadoService, sessaoRepository, votoRepository, pautaRepository,
                sessaoParaSessaoDto, sessaoFormParaSessao, resultadoDaVotacaoDto);
    }

    @Test
    public void listaUmaListaDeSessoes() {
        List<Sessao> Sessoes = Arrays.asList(sessaoAbertaMock, sessaoEncerradaMock);
        List<SessaoDto> sessaoDtosBruto = Arrays.asList(new SessaoDto());
        when(sessaoRepository.findAll()).thenReturn(Sessoes);
        when(sessaoParaSessaoDto.converterList(eq(Sessoes))).thenReturn(sessaoDtosBruto);
        List<SessaoDto> sessaoDtos = sessaoService.listaAsSessoes();
        assertEquals(sessaoDtos, sessaoDtosBruto);
        verify(sessaoRepository).findAll();
    }

    @Test
    public void listaUmaSessao() {
        Optional<Sessao> optionalDeSessao = Optional.ofNullable(sessaoAbertaMock);
        List<Sessao> Sessoes = Collections.singletonList(optionalDeSessao.get());
        List<SessaoDto> sessaoDtosBruto = Arrays.asList(new SessaoDto());
        when(sessaoRepository.findById(eq(TEST_ID))).thenReturn(optionalDeSessao);
        when(sessaoParaSessaoDto.converterList(eq(Sessoes))).thenReturn(sessaoDtosBruto);

        List<SessaoDto> sessaoDtos = sessaoService.listaAsSessoes();

        assertEquals(sessaoDtos, sessaoDtosBruto);
        verify(sessaoParaSessaoDto).converterList(eq(Sessoes));
    }

    @Test
    public void salvarPeloFormSucesso() throws TempoInvalidoException, PautaInexistenteException {
        SessaoForm teste = new SessaoForm("60", TEST_TITULO);

        when(pautaRepository.findByTitulo(eq(TEST_TITULO))).thenReturn(Arrays.asList(pautaMock));
        when(sessaoFormParaSessao.converter(eq(teste), eq(pautaRepository))).thenReturn(sessaoAbertaMock);
        when(sessaoRepository.save(eq(sessaoAbertaMock))).thenReturn(sessaoAbertaMock);

        Sessao sessao = sessaoService.salvaPeloForm(teste);
        assertEquals(sessao, sessaoAbertaMock);
        verify(sessaoFormParaSessao).converter(eq(teste), eq(pautaRepository));

    }

    @Test
    public void testafindByIdErro() {
        assertThrows(RecordNotFoundException.class, () ->
                        sessaoService.findById(TEST_ID),
                "Record not found" + TEST_ID
        );
    }

    @Test
    public void testafindByIdSucesso() {

        Optional<Sessao> optionalResultado = Optional.ofNullable(sessaoAbertaMock);
        when(sessaoRepository.findById(eq(TEST_ID))).thenReturn(optionalResultado);
        assertEquals(sessaoAbertaMock, sessaoService.findById(TEST_ID));
        verify(sessaoRepository).findById(eq(TEST_ID));
    }

    @Test
    public void testaVotaSucesso() throws AssociadoInexistenteException, AssociadoJaVotouException {

        Voto votoTest = new Voto(TEST_VOTO_SIM, associadoMock);
        Optional<Sessao> optionalResultado = Optional.ofNullable(sessaoAbertaMock);
        SessaoDto sessaoDto = new SessaoDto();
        VotaNaSessaoForm votaNaSessaoForm = new VotaNaSessaoForm("SIM", "TEST_CPF");
        when(sessaoRepository.findById(eq(TEST_ID))).thenReturn(optionalResultado);
        when(associadoService.findByCPF(eq(TEST_CPF))).thenReturn(associadoMock);
        when(votoRepository.save(eq(votoTest))).thenReturn(votoTest);
        when(sessaoParaSessaoDto.converter(any(Sessao.class))).thenReturn(sessaoDto);


        SessaoDto vota = sessaoService.vota(TEST_ID, votaNaSessaoForm);
        assertEquals(vota, sessaoDto);
        verify(sessaoRepository).findById(eq(TEST_ID));
        verify(sessaoParaSessaoDto).converter(any(Sessao.class));

    }

    @Test
    public void testaVotoFalhaSessaoEncerrada() {
        Voto votoTest = new Voto(TEST_VOTO_SIM, associadoMock);
        Optional<Sessao> optionalResultado = Optional.ofNullable(sessaoEncerradaMock);
        SessaoDto sessaoDto = new SessaoDto();
        VotaNaSessaoForm votaNaSessaoForm = new VotaNaSessaoForm("SIM", "TEST_CPF");
        when(sessaoRepository.findById(eq(TEST_ID))).thenReturn(optionalResultado);
        assertThrows(SessaoEncerradaException.class,
                () -> sessaoService.vota(TEST_ID, votaNaSessaoForm),
                "Sessao Encerada " + TEST_ID);
    }

    @Test
    public void testaVotoFalhaCPFJaVotou() {
        Optional<Sessao> optionalResultado = Optional.ofNullable(sessaoAbertaMock);
        VotaNaSessaoForm votaNaSessaoForm = new VotaNaSessaoForm("SIM", TEST_CPF);
        when(sessaoRepository.findById(eq(TEST_ID))).thenReturn(optionalResultado);
        assertThrows(AssociadoJaVotouException.class,
                () -> sessaoService.vota(TEST_ID, votaNaSessaoForm),
                "Associado do CPF: " + TEST_CPF + " já votou.");
        verify(sessaoRepository).findById(eq(TEST_ID));
    }


    private static Sessao generateSessaoEncerrada() throws TempoInvalidoException {
        Sessao sessaoEncerrada = new SessaoBuilder()
                .voto(TEST_VOTO_SIM, associadoMock)
                .voto(TEST_VOTO_NAO, associadoMock)
                .paraA(pautaMock)
                .encerra()
                .constroi();
        return sessaoEncerrada;
    }

    private static Sessao generateSessao() throws TempoInvalidoException {
        Sessao sessaoEncerrada = new SessaoBuilder()
                .voto(TEST_VOTO_SIM, associadoMock)
                .voto(TEST_VOTO_NAO, associadoMock)
                .paraA(pautaMock)
                .constroi();
        return sessaoEncerrada;
    }

    private static Associado generateAssociado() {
        return new Associado(TEST_ID, TEST_NOME, TEST_CPF);
    }

    private static Pauta generatePauta() {
        return new Pauta(TEST_ID, TEST_TITULO, TEST_CONTEUDO);
    }

}
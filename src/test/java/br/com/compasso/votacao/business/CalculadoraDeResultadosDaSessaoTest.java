package br.com.compasso.votacao.business;

import br.com.compasso.votacao.controllers.dto.VotoDto;
import br.com.compasso.votacao.model.OpcaoDeVoto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculadoraDeResultadosDaSessaoTest {

    private static List<VotoDto> setVotoDtoTest;
    private static CalculadoraDeResultadosDaSessao calculadora;


    @BeforeAll
    public static void setUp(){
        setVotoDtoTest = generateSetVotoDto();
        calculadora = new CalculadoraDeResultadosDaSessao();
    }

    @Test
    public void testaCalculaSucessoVotoSim(){
        List<String> resultados = calculadora.calcula(setVotoDtoTest);
        assertTrue(resultados.get(0).endsWith("2.0"));
    }

    @Test
    public void testaCalculaSucessoVotoNao(){
        List<String> resultados = calculadora.calcula(setVotoDtoTest);
        assertTrue(resultados.get(1).endsWith("3.0"));
    }

    @Test
    public void testaCalculaSucessoVotoTotais(){
        List<String> resultados = calculadora.calcula(setVotoDtoTest);
        assertTrue(resultados.get(2).endsWith("5.0"));
    }

    @Test
    public void testaCalculaSucessoVotoComparativo(){
        List<String> resultados = calculadora.calcula(setVotoDtoTest);
        assertTrue(resultados.get(3).contains("40,00%") && resultados.get(3).contains("60,00%"));
    }

    private static List<VotoDto> generateSetVotoDto(){
        VotoDto votoDto1 = new VotoDto();
        VotoDto votoDto2 = new VotoDto();
        VotoDto votoDto3 = new VotoDto();
        VotoDto votoDto4 = new VotoDto();
        VotoDto votoDto5 = new VotoDto();
        List<VotoDto> votoDtoList = new ArrayList<>();

        votoDto1.setOpcaoDeVoto(OpcaoDeVoto.SIM);
        votoDto2.setOpcaoDeVoto(OpcaoDeVoto.SIM);
        votoDtoList.add(votoDto1);
        votoDtoList.add(votoDto2);

        votoDto3.setOpcaoDeVoto(OpcaoDeVoto.NAO);
        votoDto4.setOpcaoDeVoto(OpcaoDeVoto.NAO);
        votoDto5.setOpcaoDeVoto(OpcaoDeVoto.NAO);
        votoDtoList.add(votoDto3);
        votoDtoList.add(votoDto4);
        votoDtoList.add(votoDto5);

        return votoDtoList;
    }
}
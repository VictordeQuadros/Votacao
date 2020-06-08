package br.com.compasso.votacao.business;

import br.com.compasso.votacao.controllers.dto.VotoDto;
import br.com.compasso.votacao.model.OpcaoDeVoto;

import java.util.Arrays;
import java.util.List;

public class CalculadoraDeResultadosDaSessao {

    public List<String> calcula(List<VotoDto> listaDeVotos) {

        double sim = 0;
        double nao = 0;
        double total = 0;
        for (VotoDto votoDto : listaDeVotos) {
            total++;
            if (votoDto.getOpcaoDeVoto() == OpcaoDeVoto.SIM) {
                sim++;
            } else {
                nao++;
            }
        }
        return montaStringResultado(sim, nao, total);
    }

    private List<String> montaStringResultado(double sim, double nao, double total) {

        String numeroDeSim = "A quantidade votos SIM: " + sim;
        String numeroDeNao = "A quantidade votos NAO: " + nao;
        String numeroTotalDeVotos = "A quantidade total de votos foi: " + total;
        String comparativo = getStringComparativa(sim, nao, total);

        return Arrays.asList(numeroDeSim, numeroDeNao, numeroTotalDeVotos, comparativo);
    }

    private String getStringComparativa(double sim, double nao, double total) {

        String votosSimFormatados ="Votos Sim: " + formataNumerosParaPorcentagemEmString(sim, total) + "%";
        String votosNaoFormatados ="Votos Nao: " + formataNumerosParaPorcentagemEmString(nao, total) + "%";

        String resultados = votosSimFormatados + " VS " + votosNaoFormatados;

        return resultados;
    }

    private String formataNumerosParaPorcentagemEmString(double votos, double total) {
        String porcentagem = String.format("%.2f", votos * 100 / total);

        return porcentagem;
    }


}

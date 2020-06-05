package br.com.compasso.votacao.exception;

public class SessaoEncerradaException extends RuntimeException{
    public SessaoEncerradaException(Long id){
        super("Sessao Encerada " + id);
    }
}

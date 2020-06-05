package br.com.compasso.votacao.exception;

public class AssociadoJaVotouException extends Exception{

	private static final long serialVersionUID = 1L;

	public AssociadoJaVotouException() {
		super();
	}

	public AssociadoJaVotouException(String message) {
		super(message);
	}
}

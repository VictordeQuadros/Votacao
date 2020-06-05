package br.com.compasso.votacao.exception;

public class AssociadoInexistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	public AssociadoInexistenteException() {
		super();
	}

	public AssociadoInexistenteException(String message) {
		super(message);
	}
}

package br.com.compasso.votacao.exception;

public class PautaInexistenteException extends Exception {

	private static final long serialVersionUID = 1L;

	public PautaInexistenteException() {
		super();
	}

	public PautaInexistenteException(String message) {
		super(message);
	}

}

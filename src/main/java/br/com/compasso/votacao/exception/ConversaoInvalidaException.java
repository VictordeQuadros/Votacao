package br.com.compasso.votacao.exception;

public class ConversaoInvalidaException extends Exception{

	private static final long serialVersionUID = 1L;

	public ConversaoInvalidaException() {
		super();
	}

	public ConversaoInvalidaException(String message) {
		super(message);
	}

}

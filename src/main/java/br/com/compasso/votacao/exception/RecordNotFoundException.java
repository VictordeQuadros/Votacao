package br.com.compasso.votacao.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(Long id){
        super("Record not found" + id);
    }
}

package br.com.gd.pagarme.exceptions;

public class PagarMeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PagarMeException(String message) {
        super(message);
    }
}

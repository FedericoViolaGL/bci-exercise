package org.globallogic.exception;

public class TokenNotValidException extends RuntimeException {
    public TokenNotValidException() {
        super("Token is not valid.");
    }
}

package org.globallogic.exception;

public class NotValidCredentialsException extends RuntimeException {
    public NotValidCredentialsException() {
        super("Credentials are not valid.");
    }
}

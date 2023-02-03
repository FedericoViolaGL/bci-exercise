package org.globallogic.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String email) {
        super(String.format("User %s already exist.", email));
    }
}

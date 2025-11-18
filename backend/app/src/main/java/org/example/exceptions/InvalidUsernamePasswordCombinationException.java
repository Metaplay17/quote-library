package org.example.exceptions;

public class InvalidUsernamePasswordCombinationException extends RuntimeException {
    public InvalidUsernamePasswordCombinationException() {
        super("Не найден комбинация username + password");
    }
}

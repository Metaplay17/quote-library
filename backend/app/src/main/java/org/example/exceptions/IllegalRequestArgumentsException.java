package org.example.exceptions;

public class IllegalRequestArgumentsException extends RuntimeException {
    public IllegalRequestArgumentsException(String wrongField) {
        super("Проверьте заполнение поля: " + wrongField);
    }
}

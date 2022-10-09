package org.example.controllers;

public class LooseException extends RuntimeException {
    public LooseException(String message) {
        super(message);
    }
}

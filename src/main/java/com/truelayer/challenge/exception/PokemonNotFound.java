package com.truelayer.challenge.exception;

public class PokemonNotFound extends RuntimeException {

    public PokemonNotFound(String message) {
        super(message);
    }

    public PokemonNotFound(String message, Throwable cause) {
        super(message, cause);
    }

}

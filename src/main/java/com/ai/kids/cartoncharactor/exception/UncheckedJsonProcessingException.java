package com.ai.kids.cartoncharactor.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.UncheckedIOException;

public class UncheckedJsonProcessingException extends UncheckedIOException {
    public UncheckedJsonProcessingException(JsonProcessingException cause) {
        super(cause.getMessage(), cause);
    }
}

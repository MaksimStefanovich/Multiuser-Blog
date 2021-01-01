package com.stefanovich.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Integer id) {
        super(entityName);
    }
}

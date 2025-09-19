package com.nttdata.dockerized.postgresql.exceptions;

public class InternalServerErrorException extends RuntimeException{

    public InternalServerErrorException(String message) {
        super(message);
    }

}

package com.nttdata.dockerized.postgresql.exceptions;

import lombok.Data;

@Data
public class Error {

    private String code;
    private String message;

}

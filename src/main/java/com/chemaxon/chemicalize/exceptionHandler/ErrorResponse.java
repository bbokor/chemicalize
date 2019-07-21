package com.chemaxon.chemicalize.exceptionHandler;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse extends Exception{
    private Date timestamp;
    private String status;
    private String error;
    private String message;
    private String path;
}

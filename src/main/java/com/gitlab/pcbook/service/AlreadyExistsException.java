package com.gitlab.pcbook.service;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException(String message){

        super(message);

    }
}


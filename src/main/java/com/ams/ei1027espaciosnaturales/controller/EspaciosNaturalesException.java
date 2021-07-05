package com.ams.ei1027espaciosnaturales.controller;

public class EspaciosNaturalesException extends RuntimeException{
    private String message;
    private String errorName;
    private String path;

    public EspaciosNaturalesException(String message, String errorName, String path) {
        this.message = message;
        this.errorName = errorName;
        this.path = path;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

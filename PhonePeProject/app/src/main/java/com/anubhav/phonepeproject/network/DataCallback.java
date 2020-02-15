package com.anubhav.phonepeproject.network;

public class DataCallback<T> {

    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    public static final int NEUTRAL = 3;

    private String errorMessage;
    private int statusCode;
    private T responseData;

    public DataCallback(String errorMessage) {
        this.errorMessage = errorMessage;
        this.statusCode = FAILURE;
    }

    public DataCallback(T responseData) {
        this.responseData = responseData;
        this.statusCode = SUCCESS;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getResponseData() {
        return responseData;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
}

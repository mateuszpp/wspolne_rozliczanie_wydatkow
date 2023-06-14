package com.aproteam.ioucash.model;

/**
 * A class representing the result of an operation (successful or not).
 */
public class Result {

    public boolean success;
    public String errorMessage;

    public Result() {
        this.success = true;
        this.errorMessage = null;
    }

    public Result(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

}
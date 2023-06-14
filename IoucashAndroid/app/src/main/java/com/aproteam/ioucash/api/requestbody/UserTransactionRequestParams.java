package com.aproteam.ioucash.api.requestbody;

public class UserTransactionRequestParams {
    public String sender;

    public String receiver;
    public double amount;

    public UserTransactionRequestParams(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
}

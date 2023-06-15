package com.aproteam.ioucash.api.requestbody;

import com.aproteam.ioucash.model.Transaction;

/**
 * Represents the parameters for a user transaction request.
 */
public class UserTransactionRequestParams {
    public String sender;

    public String receiver;
    public double amount;

    public UserTransactionRequestParams(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public UserTransactionRequestParams(Transaction transaction){
        this.sender=transaction.sender.username;
        this.receiver=transaction.receiver.username;
        this.amount=transaction.amount;
    }
}

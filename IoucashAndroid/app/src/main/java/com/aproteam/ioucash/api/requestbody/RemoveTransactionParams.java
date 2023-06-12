package com.aproteam.ioucash.api.requestbody;

public class RemoveTransactionParams {
        public String sender;
        public String receiver;

        public String token;

    public RemoveTransactionParams(String sender, String receiver, String token) {
        this.sender = sender;
        this.receiver = receiver;
        this.token = token;
    }
}

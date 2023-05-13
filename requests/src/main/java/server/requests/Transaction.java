package server.requests;

import jakarta.persistence.*;
import server.requests.Users;

import java.time.LocalDate;
import java.util.Date;


@Entity
public class Transaction extends Users{
    @Id
     int id;
     @ManyToOne
     Users sender;
     @ManyToOne
     Users receiver;
     double amount;
    LocalDate date;
    public Transaction(){}
    public Transaction(int id, Users sender, Users receiver, double amount, LocalDate date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
    }

    public int getIdd() {
        return id;
    }

    public Users getSender() {
        return sender;
    }

    public Users getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction #" + id + " | " + sender.getUsername() + " -> " + receiver.getUsername() + " | Amount: " + amount + " | Date: " + date;
    }
}


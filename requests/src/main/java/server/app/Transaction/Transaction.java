package server.app.Transaction;
import jakarta.persistence.*;
import server.app.Users.Users;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    private @Id @GeneratedValue Long id;

    @ManyToOne
    @JoinColumn(name="user_sender_id", nullable = false)
    private Users sender;

    @ManyToOne
    @JoinColumn(name="user_receiver_id", nullable = false)
    private Users receiver;
    private double amount;
    private LocalDateTime date;

    Transaction() {};

    public Transaction(Users sender, Users receiver, double amount, LocalDateTime date) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
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

import java.time.LocalDate;
import java.util.Date;

public class Transaction {
    final int id;
    final User sender;
    final User receiver;
    final double amount;
    final LocalDate date;

    public Transaction(int id, User sender, User receiver, double amount, LocalDate date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
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

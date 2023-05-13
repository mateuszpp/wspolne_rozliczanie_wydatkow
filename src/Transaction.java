import java.util.Date;

public class Transaction {
    final int id;
    final User sender;
    final User receiver;
    final int amount;
    final Date date;

    public Transaction(int id, User sender, User receiver, int amount, Date date) {
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

    public int getAmount() {
        return amount;
    }
}

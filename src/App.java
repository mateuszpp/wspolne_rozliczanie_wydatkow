import java.io.IOException;
import java.util.Date;
import javax.swing.*;

public class App extends JFrame {
    private JFrame f;
    private User user;
    private Client client;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("", 11);
        client.sendMessage(Protocol.newTransaction(new Transaction(1, new User("", 12, "pass"), new User("", 1, "aa"), 1212, new Date())));
        TransactionReader.readTransactions("asd");
    }
}

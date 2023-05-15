import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class App extends JFrame {
    private JFrame f;
    private User user;
    private Client client;

    /*public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Client client = new Client();
        client.startConnection("", 11);
        client.sendMessage(Protocol.newTransaction(new Transaction(1, new User("", 12, "pass", 0), new User("", 1, "aa", 0), 1212, new Date())));
        TransactionReader.readTransactions("asd");




    }



     */


     public static void main(String[] args) throws NoSuchAlgorithmException {


        // Tworzymy użytkowników
        User user1 = new User("1", 1,"wat",  0);
        User user2 = new User("2", 2,"wat",  0);
        User user3 = new User("3", 3,"wat",  0);
        User user4 = new User("4", 4,"wat",  0);
        User user5 = new User("5", 5,"wat",  0);
        User user6 = new User("6", 5,"wat",  0);

        ArrayList<User> users = new ArrayList<User>();



        // Tworzymy listę transakcji
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        Transaction tran1 = new Transaction(1, user1, user3, 750.0, LocalDate.of(2021, 4, 1));
        Transaction tran2 =new Transaction(2, user1, user2, 200.0, LocalDate.of(2021, 4, 2));
         Transaction tran3 =new Transaction(2, user3, user2, 250.0, LocalDate.of(2021, 4, 2));
         Transaction tran6 =new Transaction(2, user1, user2, 100.0, LocalDate.of(2021, 4, 2));
         Transaction tran8 =new Transaction(2, user2, user3, 1000.0, LocalDate.of(2021, 4, 2));



        Transaction tran4 =new Transaction(4, user4, user5, 600.0, LocalDate.of(2021, 4, 4));
        Transaction tran7 =new Transaction(7, user4, user6, 800.0, LocalDate.of(2021, 4, 7));
         Transaction tran9 =new Transaction(7, user4, user5, 1200.0, LocalDate.of(2021, 4, 7));
         Transaction tran10 =new Transaction(7, user6, user5, 200.0, LocalDate.of(2021, 4, 7));
         Transaction tran11=new Transaction(7, user4, user6, 400.0, LocalDate.of(2021, 4, 7));





         TransactionGraph transactionGraph = new TransactionGraph(transactions, users);
        // Tworzymy graf transakcji

         transactionGraph.addTransaction(tran1);
         transactionGraph.addTransaction(tran2);
         transactionGraph.addTransaction(tran3);
         transactionGraph.addTransaction(tran4);
         transactionGraph.addTransaction(tran6);
         transactionGraph.addTransaction(tran7);
         transactionGraph.addTransaction(tran8);
         transactionGraph.addTransaction(tran9);
         transactionGraph.addTransaction(tran10);
         transactionGraph.addTransaction(tran11);





        // Wypisujemy listę użytkowników i ich salda przed uproszczeniem
        System.out.println("Lista użytkowników i ich salda:");
        for (User user : transactionGraph.users) {
            System.out.println(user.getUsername()+" " + " "+user.getBalance());
        }

        // Wykonujemy uproszczenie transakcji

         System.out.println("lista uproszczonych transakcji");

        // System.out.println(transactionGraph.simplify(transactionGraph.users, transactionGraph.listOfTransactions));

         System.out.println(transactionGraph.getTransactionsForUser(user3));


    }

}

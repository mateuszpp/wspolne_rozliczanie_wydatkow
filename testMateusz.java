import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionGraphTest {

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    @Test
    public void testWorstExecutionTime() throws NoSuchAlgorithmException {
        TransactionGraph graph;
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        users = new ArrayList<>();
        transactions = new ArrayList<>();
        graph = new TransactionGraph(transactions, users);

        int numUsers = 10000;
        for (int i = 0; i < numUsers; i++) {
            users.add(new User("User", 10,"password",0));
        }
        Random rand = new Random();
        User sender = users.get(rand.nextInt(numUsers));
        User receiver = users.get(rand.nextInt(numUsers));
        while (sender == receiver) {
            receiver = users.get(rand.nextInt(numUsers));
        }
        int numTransactions = 100000;
        for (int i = 0; i < numTransactions; i++) {
            transactions.add(new Transaction(i, sender, receiver, rand.nextDouble(), LocalDate.now()));
        }
        long startTime = System.currentTimeMillis();
        graph.simplify(users, transactions);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        assertTrue(duration < 1000);
    }

    @Test
    public void testAverageCaseExecutionTime() throws NoSuchAlgorithmException {
     TransactionGraph graph;
     ArrayList<User> users = new ArrayList<>();
     ArrayList<Transaction> transactions = new ArrayList<>();

        users = new ArrayList<>();
        transactions = new ArrayList<>();
        graph = new TransactionGraph(transactions, users);

        int numUsers = 1000;
        for (int i = 0; i < numUsers; i++) {
            users.add(new User("User", 10,"password",0));
        }
        Random rand = new Random();
        int numTransactions = 10000;
        for (int i = 0; i < numTransactions; i++) {
            User sender = users.get(rand.nextInt(numUsers));
            User receiver = users.get(rand.nextInt(numUsers));
            while (sender == receiver) {
                receiver = users.get(rand.nextInt(numUsers));
            }
            transactions.add(new Transaction(i, sender, receiver, rand.nextDouble(), LocalDate.now()));
        }
        long startTime = System.currentTimeMillis();
        System.out.println(graph.simplify(users, transactions));
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        assertTrue(duration < 100);
    }


    @Test
    public void test5() throws NoSuchAlgorithmException {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        users = new ArrayList<>();
        transactions = new ArrayList<>();

        User user1 = new User("piotr1", 1, "passwd1",0);
        User user2 = new User("piotr2", 2, "passwd2",0);
        User user3 = new User("piotr3", 3, "passwd3",0);
        User user4 = new User("piotr4", 4, "passwd4",0);
        User user5 = new User("piotr5", 5, "passwd5",0);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        TransactionGraph graph = new TransactionGraph(transactions, users);

        graph.addTransaction(new Transaction(1, user2, user1, 20, LocalDate.now()));
        graph.addTransaction(new Transaction(2, user5, user2, 40, LocalDate.now()));
        graph.addTransaction(new Transaction(3, user5, user4, 10, LocalDate.now()));
        graph.addTransaction(new Transaction(4, user3, user4, 20, LocalDate.now()));
        graph.addTransaction(new Transaction(5, user1, user4, 10, LocalDate.now()));

        List<Transaction> list = graph.simplify(graph.users, graph.listOfTransactions);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getReceiver().getUsername() + " -> " +
                    list.get(i).getSender().getUsername() + " " + list.get(i).getAmount());
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration);
    }

    @Test
    public void test1000() throws NoSuchAlgorithmException {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        users = new ArrayList<>();
        transactions = new ArrayList<>();

        for(int i = 0; i < 1000; i++) {
            users.add(new User(Integer.toString(i), i, "passwd", 0));
        }

        TransactionGraph graph = new TransactionGraph(transactions, users);

        for(int i = 0; i < 1000; i++) {
            User sender = users.get(getRandomNumber(1,999));
            User receiver = users.get(getRandomNumber(1,999));
            graph.addTransaction(new Transaction(i, sender,
                    receiver,getRandomNumber(0,10),LocalDate.now()));
        }

        /*
        List<Transaction> list = graph.simplify(graph.users, graph.listOfTransactions);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getReceiver().getUsername() + " -> " +
                    list.get(i).getSender().getUsername() + " " + list.get(i).getAmount());
        }
        */
        graph.simplify(graph.users, graph.listOfTransactions);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration);
    }

    @Test
    public void test10000() throws NoSuchAlgorithmException {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        users = new ArrayList<>();
        transactions = new ArrayList<>();

        for(int i = 0; i < 10000; i++) {
            users.add(new User(Integer.toString(i), i, "passwd", 0));
        }

        TransactionGraph graph = new TransactionGraph(transactions, users);

        for(int i = 0; i < 10000; i++) {
            User sender = users.get(getRandomNumber(1,9999));
            User receiver = users.get(getRandomNumber(1,9999));
            graph.addTransaction(new Transaction(i, sender,
                    receiver,getRandomNumber(0,10),LocalDate.now()));
        }

        /*
        List<Transaction> list = graph.simplify(graph.users, graph.listOfTransactions);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getReceiver().getUsername() + " -> " +
                    list.get(i).getSender().getUsername() + " " + list.get(i).getAmount());
        }
        */
        graph.simplify(graph.users, graph.listOfTransactions);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration);
    }

    @Test
    public void test20000() throws NoSuchAlgorithmException {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        users = new ArrayList<>();
        transactions = new ArrayList<>();

        for(int i = 0; i < 20000; i++) {
            users.add(new User(Integer.toString(i), i, "passwd", 0));
        }

        TransactionGraph graph = new TransactionGraph(transactions, users);

        for(int i = 0; i < 20000; i++) {
            User sender = users.get(getRandomNumber(1,19999));
            User receiver = users.get(getRandomNumber(1,19999));
            graph.addTransaction(new Transaction(i, sender,
                    receiver,getRandomNumber(0,10),LocalDate.now()));
        }

        /*
        List<Transaction> list = graph.simplify(graph.users, graph.listOfTransactions);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getReceiver().getUsername() + " -> " +
                    list.get(i).getSender().getUsername() + " " + list.get(i).getAmount());
        }
        */
        graph.simplify(graph.users, graph.listOfTransactions);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration);
    }

    @Test
    public void test20000Value10000() throws NoSuchAlgorithmException {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        users = new ArrayList<>();
        transactions = new ArrayList<>();

        for(int i = 0; i < 20000; i++) {
            users.add(new User(Integer.toString(i), i, "passwd", 0));
        }

        TransactionGraph graph = new TransactionGraph(transactions, users);

        for(int i = 0; i < 20000; i++) {
            User sender = users.get(getRandomNumber(1,19999));
            User receiver = users.get(getRandomNumber(1,19999));
            graph.addTransaction(new Transaction(i, sender,
                    receiver,getRandomNumber(0,10000),LocalDate.now()));
        }

        /*
        List<Transaction> list = graph.simplify(graph.users, graph.listOfTransactions);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getReceiver().getUsername() + " -> " +
                    list.get(i).getSender().getUsername() + " " + list.get(i).getAmount());
        }
        */
        graph.simplify(graph.users, graph.listOfTransactions);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration);
    }

    @Test
    public void test20000Value10000000() throws NoSuchAlgorithmException {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        users = new ArrayList<>();
        transactions = new ArrayList<>();

        for(int i = 0; i < 20000; i++) {
            users.add(new User(Integer.toString(i), i, "passwd", 0));
        }

        TransactionGraph graph = new TransactionGraph(transactions, users);

        for(int i = 0; i < 20000; i++) {
            User sender = users.get(getRandomNumber(1,19999));
            User receiver = users.get(getRandomNumber(1,19999));
            graph.addTransaction(new Transaction(i, sender,
                    receiver,getRandomNumber(0,10000000),LocalDate.now()));
        }

        /*
        List<Transaction> list = graph.simplify(graph.users, graph.listOfTransactions);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getReceiver().getUsername() + " -> " +
                    list.get(i).getSender().getUsername() + " " + list.get(i).getAmount());
        }
        */
        graph.simplify(graph.users, graph.listOfTransactions);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration);
    }

    @Test
    public void test25000Value10000000() throws NoSuchAlgorithmException {

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        users = new ArrayList<>();
        transactions = new ArrayList<>();

        for(int i = 0; i < 25000; i++) {
            users.add(new User(Integer.toString(i), i, "passwd", 0));
        }

        TransactionGraph graph = new TransactionGraph(transactions, users);

        for(int i = 0; i < 25000; i++) {
            User sender = users.get(getRandomNumber(1,24999));
            User receiver = users.get(getRandomNumber(1,24999));
            graph.addTransaction(new Transaction(i, sender,
                    receiver,getRandomNumber(9000000,10000000),LocalDate.now()));
        }

        /*
        List<Transaction> list = graph.simplify(graph.users, graph.listOfTransactions);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getReceiver().getUsername() + " -> " +
                    list.get(i).getSender().getUsername() + " " + list.get(i).getAmount());
        }
        */
        graph.simplify(graph.users, graph.listOfTransactions);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration);
    }


}

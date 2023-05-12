
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionGraphTest {
    private TransactionGraph graph;
    private ArrayList<User> users;
    private ArrayList<Transaction> transactions;

    @BeforeEach
    public void setUp() {
        users = new ArrayList<>();
        transactions = new ArrayList<>();
        graph = new TransactionGraph(transactions, users);
    }

    @Test
    public void testExecutionTime() throws NoSuchAlgorithmException {
        // W najgorszym przypadku wszystkie transakcje będą między dwoma osobami, a użytkowników będzie bardzo dużo
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
        assertTrue(duration < 1000); // Oczekiwany czas wykonania: poniżej 1 sekundy
    }

    @Test
    public void testAverageCaseExecutionTime() throws NoSuchAlgorithmException {
        // W przypadku średnim użytkowników będzie mniej, a transakcje będą między różnymi osobami
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
        graph.simplify(users, transactions);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        assertTrue(duration < 100); // Oczekiwany czas wykonania: poniżej 0,1 sekundy
    }
}
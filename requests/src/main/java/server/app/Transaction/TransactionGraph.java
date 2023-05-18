package server.app.Transaction;

import server.app.Users.Users;

import java.time.LocalDate;
import java.util.*;
public class TransactionGraph {
    static ArrayList<Transaction> listOfTransactions = new ArrayList<>();
    static ArrayList<Users> users = new ArrayList<>();
    static ArrayList<Transaction> simiplifiedList = new ArrayList<>();
    private static long lastTransactionId =0;

    private static long getNextTransactionId() {
        lastTransactionId++;
        return lastTransactionId;
    }
    public TransactionGraph(ArrayList<Transaction> listOfTransactions, ArrayList<Users> users) {
        listOfTransactions = listOfTransactions;
        users = new ArrayList<Users>();
    }
    public static void addTransaction(Transaction transaction) {

        if (!users.contains(transaction.getSender())) {
            users.add(transaction.getSender());
        }
        if (!users.contains(transaction.getReceiver())) {
            users.add(transaction.getReceiver());
        }

        Users sender = transaction.getSender();
        Users receiver = transaction.getReceiver();
        double amount = transaction.getAmount();

        sender.setBalance(sender.getBalance() - amount);

        receiver.setBalance(receiver.getBalance() + amount);
        System.out.println(receiver.getBalance()+receiver.getUsername());
        System.out.println();

        listOfTransactions.add(transaction);

        //simplify(users, listOfTransactions); //?? im not sure if it should be there, however i think that the graph should be simplified every time there is new added transaction
    }

    public static ArrayList<Transaction> simplify() {
        ArrayList<Transaction> simplifiedList = new ArrayList<>();
        System.out.println(listOfTransactions + "simplify przed");

        // find not connected subgraphs
        List<Set<Users>> subGraphs = findSubGraphs(users, listOfTransactions);

        // Dla każdego niepołączonego podgrafu znajdź najkrótszą ścieżkę i dokonaj uproszczenia
        //for each not connected subgraph find the shortest path and make simplification
        for (Set<Users> subGraph : subGraphs) {
            ArrayList<Users> subGraphUsers = new ArrayList<>(subGraph);

            while (findUserWithMaxBalance(subGraphUsers) != null || findUserWithMinBalance(subGraphUsers) != null) {

                Users debitor = findUserWithMaxBalance(subGraphUsers);
                Users borrower = findUserWithMinBalance(subGraphUsers);
                double debitorbalance = debitor.getBalance();
                double borrowerbalance = borrower.getBalance();
                double transactionsize = 0;
                if (Math.abs(debitorbalance) > Math.abs(borrowerbalance)) {
                    transactionsize = Math.abs(borrower.getBalance());
                } else {
                    transactionsize = Math.abs(debitor.getBalance());
                }
                Transaction Simplifiedtransaction = new Transaction(getNextTransactionId(),debitor, borrower, transactionsize, LocalDate.now(), true);
                simplifiedList.add(Simplifiedtransaction);

                debitor.setBalance(debitorbalance - transactionsize);
                borrower.setBalance(borrowerbalance + transactionsize);
            }
        }
        TransactionGraph.simiplifiedList = simplifiedList;
        System.out.println(listOfTransactions+ "po simplify ");
        for (Users user: users) {
            System.out.println(user.getBalance());
        }
        return simplifiedList;
    }
    public static List<Set<Users>> findSubGraphs(ArrayList<Users> users, ArrayList<Transaction> listOfTransactions) {
        List<Set<Users>> subGraphs = new ArrayList<>();

        // Create a map that associates each user with the set of users they have transacted with
        Map<Users, Set<Users>> userToFriends = new HashMap<>();
        for (Users user : users) {
            userToFriends.put(user, new HashSet<>());
        }
        for (Transaction transaction : listOfTransactions) {
            Users sender = transaction.getSender();
            Users receiver = transaction.getReceiver();
            userToFriends.get(sender).add(receiver);
            userToFriends.get(receiver).add(sender);
        }

        // Traverse the graph to find all disconnected subgraphs
        Set<Users> visitedUsers = new HashSet<>();
        for (Users user : users) {
            if (!visitedUsers.contains(user)) {
                Set<Users> subGraph = new HashSet<>();
                traverseGraph(user, userToFriends, visitedUsers, subGraph);
                subGraphs.add(subGraph);
            }
        }
        return subGraphs;
    }

    private static void traverseGraph(Users user, Map<Users, Set<Users>> userToFriends, Set<Users> visitedUsers, Set<Users> subGraph) {
        visitedUsers.add(user);
        subGraph.add(user);
        for (Users friend : userToFriends.get(user)) {
            if (!visitedUsers.contains(friend)) {
                traverseGraph(friend, userToFriends, visitedUsers, subGraph);
            }
        }
    }

    public static Users findUserWithMaxBalance(ArrayList<Users> users) {
        Users maxUser = null;
        double maxBalance = 0.0;
        for (Users user : users) {
            if (user.getBalance() > maxBalance) {
                maxBalance = user.getBalance();
                maxUser = user;
            }
        }
        return maxUser;
    }
    public static Users findUserWithMinBalance(ArrayList<Users> users) {
        Users minUser = null;
        double minBalance = 0.0;
        for (Users user : users) {
            if (user.getBalance() < minBalance) {
                minBalance = user.getBalance();
                minUser = user;
            }
        }
        return minUser;
    }
    public ArrayList<Transaction> giveTransactions() {
        return listOfTransactions;
    }

    public List<Transaction> getTransactionsForUser(Users user) {
        simplify();

        // Create a new list to store the user's transactions
        List<Transaction> userTransactions = new ArrayList<>();

        // Loop through the simplified list and add transactions that involve the user
        for (Transaction transaction : listOfTransactions) {
            if (transaction.getSender().equals(user) || transaction.getReceiver().equals(user)) {
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }

    public static void setListOfTransactions(ArrayList<Transaction> listOfTransactions) {
        listOfTransactions = listOfTransactions;
    }

    public static void setUsers(ArrayList<Users> users) {
        users = users;
    }

    public static ArrayList<Users> getUsers() {
        return users;
    }

    public static ArrayList<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }
}





import java.time.LocalDate;
import java.util.*;
public class TransactionGraph {
    ArrayList<Transaction> listOfTransactions;
    ArrayList<User> users;
    private int lastTransactionId =0;

    private int getNextTransactionId() {
        lastTransactionId++;
        return lastTransactionId;
    }
    public TransactionGraph(ArrayList<Transaction> listOfTransactions, ArrayList<User> users) {
        this.listOfTransactions = listOfTransactions;
        this.users = new ArrayList<User>();
    }
    public void addTransaction(Transaction transaction) {

        if (!users.contains(transaction.getSender())) {
            users.add(transaction.getSender());
        }
        if (!users.contains(transaction.getReceiver())) {
            users.add(transaction.getReceiver());
        }

        User sender = transaction.getSender();
        User receiver = transaction.getReceiver();
        double amount = transaction.getAmount();

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        listOfTransactions.add(transaction);
        //simplify(users, listOfTransactions); //?? im not sure if it should be there, however i think that the graph should be simplified every time there is new added transaction
    }

    public List<Transaction> simplify(ArrayList<User> users, ArrayList<Transaction> listOfTransactions) {
        ArrayList<Transaction> simplifiedList = new ArrayList<>();

        // find not connected subgraphs
        List<Set<User>> subGraphs = findSubGraphs(users, listOfTransactions);

        // Dla każdego niepołączonego podgrafu znajdź najkrótszą ścieżkę i dokonaj uproszczenia
        //for each not connected subgraph find the shortest path and make simplification
        for (Set<User> subGraph : subGraphs) {
            ArrayList<User> subGraphUsers = new ArrayList<>(subGraph);

            while (findUserWithMaxBalance(subGraphUsers) != null || findUserWithMinBalance(subGraphUsers) != null) {

                User debitor = findUserWithMaxBalance(subGraphUsers);
                User borrower = findUserWithMinBalance(subGraphUsers);
                double debitorbalance = debitor.getBalance();
                double borrowerbalance = borrower.getBalance();
                double transactionsize = 0;
                if (Math.abs(debitorbalance) > Math.abs(borrowerbalance)) {
                    transactionsize = Math.abs(borrower.getBalance());
                } else {
                    transactionsize = Math.abs(debitor.getBalance());
                }
                Transaction Simplifiedtransaction = new Transaction(getNextTransactionId(), debitor, borrower, transactionsize, LocalDate.now());
                simplifiedList.add(Simplifiedtransaction);

                debitor.setBalance(debitorbalance - transactionsize);
                borrower.setBalance(borrowerbalance + transactionsize);
            }
        }

        listOfTransactions = simplifiedList;
        return listOfTransactions;
    }
    public List<Set<User>> findSubGraphs(ArrayList<User> users, ArrayList<Transaction> listOfTransactions) {
        List<Set<User>> subGraphs = new ArrayList<>();

        // Create a map that associates each user with the set of users they have transacted with
        Map<User, Set<User>> userToFriends = new HashMap<>();
        for (User user : users) {
            userToFriends.put(user, new HashSet<>());
        }
        for (Transaction transaction : listOfTransactions) {
            User sender = transaction.getSender();
            User receiver = transaction.getReceiver();
            userToFriends.get(sender).add(receiver);
            userToFriends.get(receiver).add(sender);
        }

        // Traverse the graph to find all disconnected subgraphs
        Set<User> visitedUsers = new HashSet<>();
        for (User user : users) {
            if (!visitedUsers.contains(user)) {
                Set<User> subGraph = new HashSet<>();
                traverseGraph(user, userToFriends, visitedUsers, subGraph);
                subGraphs.add(subGraph);
            }
        }
        return subGraphs;
    }

    private void traverseGraph(User user, Map<User, Set<User>> userToFriends, Set<User> visitedUsers, Set<User> subGraph) {
        visitedUsers.add(user);
        subGraph.add(user);
        for (User friend : userToFriends.get(user)) {
            if (!visitedUsers.contains(friend)) {
                traverseGraph(friend, userToFriends, visitedUsers, subGraph);
            }
        }
    }

    public User findUserWithMaxBalance(ArrayList<User> users) {
            User maxUser = null;
            double maxBalance = 0.0;
            for (User user : users) {
                if (user.getBalance() > maxBalance) {
                    maxBalance = user.getBalance();
                    maxUser = user;
                }
            }
            return maxUser;
        }
        public User findUserWithMinBalance(ArrayList<User> users) {
            User minUser = null;
            double minBalance = 0.0;
            for (User user : users) {
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

    public List<Transaction> getTransactionsForUser(User user) {
            simplify(users, listOfTransactions);

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
}




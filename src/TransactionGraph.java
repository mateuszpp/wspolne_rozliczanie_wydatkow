import java.util.*;

public class TransactionGraph {
    ArrayList<Transaction> listOfTransactions;
    ArrayList<User> users;
    LinkedHashMap<User, HashMap<User,Integer>> graph;

    public TransactionGraph(ArrayList<Transaction> listOfTransactions,ArrayList<User> users) {
        // utworzenie grafu z listOftransaciotion
        this.listOfTransactions = listOfTransactions;
        this.users = users;
        this.graph = graphCreation(listOfTransactions, users);
    }
    public LinkedHashMap<User,HashMap<User,Integer>> getGraph(){
        return graph;
    }
    public void addTransaction(Transaction transaction) {
        this.listOfTransactions.add(transaction);
        if(!this.users.contains(transaction.sender)){
            this.users.add(transaction.sender);
        }
        if(!this.users.contains(transaction.receiver)){
            this.users.add(transaction.receiver);
        }
        this.graph = graphCreation(listOfTransactions,users);
        this.simplify();
    }


    public void simplify() {
        calculateUsersPlusesAndMinuses();
        this.graph = graphCreation(simplifyGraph(),users);
    }
    private void calculateUsersPlusesAndMinuses() {
        for(User person : graph.keySet() ){  //Oblicza jaki jest dług
            for(User borrowers : graph.get(person).keySet()){
                int x = person.getDebt();
                x = x - graph.get(person).get(borrowers);
                person.setDebt(x);
            }
            for (User debtors : graph.keySet()){  //Odejmuje od długu to co są winni nam inni użytkownicy
                if(debtors == person) continue;
                for(User x : graph.get(debtors).keySet()){

                    if(x.getUserId() == person.getUserId()){
                        int y = person.getDebt();
                        y = y + graph.get(debtors).get(x);
                        person.setDebt(y);
                    }
                }
            }
            if(person.getDebt() < 0)
                person.setDebtor(true);
        }
        for(User x : graph.keySet()){
            System.out.println(x.getUsername() + " debt: " + x.getDebt());
        }
    }
    private ArrayList<Transaction> simplifyGraph() {
        ArrayList<Transaction> listOfSimplifiedTransactions = new ArrayList<>();
        for(User person : graph.keySet()){
            Queue<User> debtorsBorrowers = new LinkedList<>();
            for(User borrower : graph.get(person).keySet()){
                if(!borrower.isDebtor() && borrower.getDebt() != 0){
                    int debt = 0;
                    if(Math.abs(person.getDebt()) > borrower.getDebt()){
                        debt = borrower.getDebt();
                    }else {
                        debt = Math.abs(person.getDebt());
                    }
                    person.setDebt(person.getDebt()+debt);
                    borrower.setDebt(borrower.getDebt()-debt);
                    if(debt != 0) {
                        Transaction transaction = new Transaction(1, person, borrower, debt, new Date());
                        listOfSimplifiedTransactions.add(transaction);
                    }
                }else if(borrower.isDebtor() && borrower.getDebt() != 0){
                    if(graph.get(borrower).keySet().contains(person)){
                        debtorsBorrowers.add(borrower);
                    }
                }
            }
            if(!debtorsBorrowers.isEmpty()){
                User receiver = debtorsBorrowers.poll();
                int debt = Math.abs(person.getDebt());
                person.setDebt(person.getDebt()+debt);
                receiver.setDebt(receiver.getDebt()-debt);
                if(debt != 0) {
                    Transaction transaction = new Transaction(2, person, receiver, debt, new Date());
                    listOfSimplifiedTransactions.add(transaction);
                }
            }
        }
        return listOfSimplifiedTransactions;
    }
    private LinkedHashMap<User,HashMap<User,Integer>> graphCreation(ArrayList<Transaction> listOfTransactions,ArrayList<User> listOfUsers){
        graph = new LinkedHashMap<>();
        for(User person : listOfUsers){
            LinkedHashMap<User,Integer> userMap = new LinkedHashMap<>();
            for(Transaction x : listOfTransactions){
                if(x.getReceiver() == person){
                    int amount = x.getAmount();
                    if(userMap.containsKey(x.getSender())){
                        amount = amount + userMap.get(x.getSender());
                    }
                    userMap.put(x.getSender(),amount);
                }
            }
            graph.put(person,userMap);
        }
        return graph;
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (User x : graph.keySet()){
            sb.append(x.getUsername() + ": ");
            for (User y : graph.get(x).keySet()){
                sb.append(y.getUsername() + " - " + graph.get(x).get(y) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public ArrayList<Transaction> giveTransactions() {
        return new ArrayList<Transaction>();
    }
}

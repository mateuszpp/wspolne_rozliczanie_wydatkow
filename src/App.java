import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

public class App extends JFrame {
    private JFrame f;
    private User user;
    private Client client;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        /*
        Client client = new Client();
        client.startConnection("", 11);
        client.sendMessage(Protocol.newTransaction(new Transaction(1, new User("", 12, "pass"), new User("", 1, "aa"), 1212, new Date())));
        TransactionReader.readTransactions("asd");
         */
        User a = new User("A",1,"aaa");
        User b = new User("B",2,"aab");
        User c = new User("C",3,"aac");
        User d = new User("D",4,"aad");
        User e = new User("E",5,"aae");
        User f = new User("F",6,"aaf");
        User g = new User("G",7,"aag");
        ArrayList<User> listOfUser = new ArrayList<>();
        listOfUser.add(a);
        listOfUser.add(b);
        listOfUser.add(c);
        listOfUser.add(d);
        listOfUser.add(e);
        listOfUser.add(f);
        listOfUser.add(g);
        Transaction transaction1 = new Transaction(1,b,a,10,new Date());
        Transaction transaction2 = new Transaction(2,c,a,10,new Date());
        Transaction transaction3 = new Transaction(3,d,a,20,new Date());
        Transaction transaction4 = new Transaction(4,f,a,30,new Date());
        Transaction transaction5 = new Transaction(5,a,b,10,new Date());
        Transaction transaction6 = new Transaction(6,c,b,10,new Date());
        Transaction transaction7 = new Transaction(7,e,b,10,new Date());
        Transaction transaction8 = new Transaction(8,g,b,30,new Date());
        Transaction transaction9 = new Transaction(9,a,c,10,new Date());
        Transaction transaction10 = new Transaction(10,d,c,20,new Date());
        Transaction transaction11 = new Transaction(11,e,c,30,new Date());
        Transaction transaction12 = new Transaction(12,f,c,40,new Date());
        Transaction transaction13 = new Transaction(13,b,d,10,new Date());
        Transaction transaction14 = new Transaction(14,g,d,30,new Date());
        Transaction transaction15 = new Transaction(15,b,e,10,new Date());
        Transaction transaction16 = new Transaction(16,f,e,20,new Date());
        Transaction transaction17 = new Transaction(17,g,e,40,new Date());
        Transaction transaction18 = new Transaction(18,a,f,10,new Date());
        Transaction transaction19 = new Transaction(19,d,f,40,new Date());
        Transaction transaction20 = new Transaction(20,a,g,10,new Date());
        Transaction transaction21 = new Transaction(21,b,g,20,new Date());
        Transaction transaction22 = new Transaction(22,c,g,30,new Date());
        Transaction transaction23 = new Transaction(23,d,g,40,new Date());

        ArrayList<Transaction> listOfTransactions = new ArrayList<>();
        listOfTransactions.add(transaction1);
        listOfTransactions.add(transaction2);
        listOfTransactions.add(transaction3);
        listOfTransactions.add(transaction4);
        listOfTransactions.add(transaction5);
        listOfTransactions.add(transaction6);
        listOfTransactions.add(transaction7);
        listOfTransactions.add(transaction8);
        listOfTransactions.add(transaction9);
        listOfTransactions.add(transaction10);
        listOfTransactions.add(transaction11);
        listOfTransactions.add(transaction12);
        listOfTransactions.add(transaction13);
        listOfTransactions.add(transaction14);
        listOfTransactions.add(transaction15);
        listOfTransactions.add(transaction16);
        listOfTransactions.add(transaction17);
        listOfTransactions.add(transaction18);
        listOfTransactions.add(transaction19);
        listOfTransactions.add(transaction20);
        listOfTransactions.add(transaction21);
        listOfTransactions.add(transaction22);
        listOfTransactions.add(transaction23);
        TransactionGraph tg = new TransactionGraph(listOfTransactions,listOfUser);
        System.out.println(tg.toString());
        tg.simplify();




        User x = new User("X",10,"aax");
        User y = new User("Y",11,"aay");
        User z = new User("Z",12,"aaz");
        User k = new User("K",13,"aak");
        User l = new User("L",14,"aal");
        User m = new User("M",15,"aam");
        ArrayList<User> idk = new ArrayList<>();
        idk.add(x);
        idk.add(y);
        idk.add(z);
        idk.add(k);
        idk.add(l);
        idk.add(m);
        Transaction transaction100 = new Transaction(100,x,y,200,new Date());
        Transaction transaction101 = new Transaction(101,x,z,750,new Date());
        Transaction transaction102 = new Transaction(102,k,l,500,new Date());
        Transaction transaction103 = new Transaction(103,k,m,600,new Date());
        ArrayList<Transaction> trans = new ArrayList<>();
        trans.add(transaction100);
        trans.add(transaction101);
        trans.add(transaction102);
        trans.add(transaction103);
        TransactionGraph graphazzo = new TransactionGraph(trans,idk);
        System.out.println(graphazzo.toString());
        graphazzo.simplify();

    }
}

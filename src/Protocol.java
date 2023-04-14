public class Protocol {
    public static String newTransaction(Transaction transaction) {
        return "";
    }
    public static String downloadTransactions(Transaction transaction) {
        return "";
    }
    public static String removeTransaction(Transaction transaction) {
        return "";
    }

    public static String registerUser(User user) {
        return ";";
    }
    public static String login(User user) {
        return "";
    }
    public static String removeUser(User user) {
        return "";
    }
    public static String changePasswd(User user, String hash) {
        return "";
    }
    public static String logout(User user) {
        return "";
    }

    public static String borrowMoney(User sender, User receiver, double amount) {
        return "";
    }

    public static int getOperation(String msg) {
        return 1;
    }
    public static String getData(String msg) {
        return "";
    }
}

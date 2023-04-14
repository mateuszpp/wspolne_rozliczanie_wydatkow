import java.util.ArrayList;

public class Database {
    private JSONParser parser = new JSONParser();

    public ArrayList<User> getUsers() {
        return new ArrayList<>();
    }

    public boolean checkPass(User user) {
        return true;
    }

    public boolean changePasswd(String hash) {
        return true;
    }

    public boolean ifUserExist(User user) {
        return true;
    }

    public boolean addUser(User user) {
        return true;
    }

    public boolean removeUser(User user) {
        return true;
    }

    private void saveDB() {

    }

    private void loadDB() {

    }

    public boolean borrowMoney(User sender, User receiver, double amount) {
        return true;
    }
}

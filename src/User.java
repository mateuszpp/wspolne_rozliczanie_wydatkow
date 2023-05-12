import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    final private String username;
    final private int userId;
    final private String hashedPasswd;

    public double balance;

    public String getHashedPasswd() {
        return hashedPasswd;
    }

    public User(String username, int userId, String hashedPasswd, double balance) throws NoSuchAlgorithmException{
        this.username = username;
        this.userId = userId;
        this.hashedPasswd = hashPassword(hashedPasswd);
        this.balance=balance;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    private String hashPassword(String passwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(passwd.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(Integer.toHexString((b & 0xFF) + 0x100).substring(1));
        }
        return sb.toString();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

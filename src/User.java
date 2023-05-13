import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class User {
    final private String username;
    final private int userId;
    final private String hashedPasswd;
    private int debt;
    private boolean isDebtor;

    public String getHashedPasswd() {
        return hashedPasswd;
    }

    public User(String username, int userId, String hashedPasswd) throws NoSuchAlgorithmException {
        this.username = username;
        this.userId = userId;
        this.hashedPasswd = hashPassword(hashedPasswd);
        this.debt = 0;
        this.isDebtor = false;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public void setDebtor(boolean debtor) {
        isDebtor = debtor;
    }

    public int getDebt() {
        return debt;
    }

    public boolean isDebtor() {
        return isDebtor;
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

}

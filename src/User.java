public class User {
    final private String username;
    final private int userId;
    final private String hashedPasswd;

    public String getHashedPasswd() {
        return hashedPasswd;
    }

    public User(String username, int userId, String hashedPasswd) {
        this.username = username;
        this.userId = userId;
        this.hashedPasswd = hashedPasswd;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }
}

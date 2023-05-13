package server.requests;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Users {
    private @Id @GeneratedValue Long id;
    private String username;
    private String hashedPasswd;

    private List<Transaction> transactions;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPasswd() {
        return hashedPasswd;
    }

    Users(){}
    public Users(String username, String hashedPasswd) {
        this.username = username;
        this.hashedPasswd = hashedPasswd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) && Objects.equals(username, users.username) && Objects.equals(hashedPasswd, users.hashedPasswd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, hashedPasswd);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", hashedPasswd='" + hashedPasswd + '\'' +
                '}';
    }

}

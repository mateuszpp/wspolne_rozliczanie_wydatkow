package server.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Long> {
    @Query("FROM Users WHERE username = 'szwedson'")
    List<Users> findBySzwedson();

}

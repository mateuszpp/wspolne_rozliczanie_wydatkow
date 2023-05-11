package server.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Long> {
    @Query("FROM Users WHERE username = 'szwedson'")
    List<Users> findBySzwedson();

    @Query("FROM Users WHERE username = :name")
    List<Users> findByName(@Param("name") String name);

}

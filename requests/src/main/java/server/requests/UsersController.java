package server.requests;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {

    private final UsersRepository repository;

    UsersController(UsersRepository repository) {
        this.repository = repository;
        repository.findAll();
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<Users> all() {
        return repository.findAll();
    }

    @GetMapping("/szwedson")
    List<Users> all2(){
        return repository.findBySzwedson();
    }

    @GetMapping("/users/{name}")
    List<Users> userByName(@PathVariable String name){
        return repository.findByName(name);
    }
}

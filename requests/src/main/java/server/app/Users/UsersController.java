package server.app.Users;

import org.springframework.web.bind.annotation.*;
import server.app.Transaction.Transaction;
import server.app.Transaction.TransactionRepository;

import java.util.List;

@RestController
public class UsersController {

    private final UsersRepository usersRepository;
    private final TransactionRepository transactionRepository;

    UsersController(UsersRepository repository, TransactionRepository transactionRepository) {
        this.usersRepository = repository;
        this.transactionRepository = transactionRepository;
        repository.findAll();
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<Users> all() {
        return usersRepository.findAll();
    }

    @GetMapping("/szwedson")
    List<Users> all2(){
        return usersRepository.findBySzwedson();
    }

    @GetMapping("/users/{name}")
    Users userByName(@PathVariable String name){
        return usersRepository.findByName(name);
    }



    @GetMapping("/transactions")
    List<Transaction> allTransactions(){return transactionRepository.findAll();}
}

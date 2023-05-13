package server.requests;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/addTransaction")
    public ResponseEntity<String> addTransaction(@RequestBody UserTransactionRequest utRequest){
        Users sender = usersRepository.findByName(utRequest.sender);
        Users receiver = usersRepository.findByName(utRequest.receiver);

        Transaction transaction = new Transaction(sender,receiver,utRequest.amount, LocalDateTime.now());
        transactionRepository.save(transaction);

        return new ResponseEntity<String>("Record saved successfully", HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    List<Transaction> allTransactions(){return transactionRepository.findAll();}
}

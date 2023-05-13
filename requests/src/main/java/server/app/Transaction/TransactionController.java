package server.app.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.app.requests.UserTransactionRequest;
import server.app.Users.Users;
import server.app.Users.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionRepository transactionrepository;
    private final UsersRepository usersRepository;

    public TransactionController(TransactionRepository transactionRepository, UsersRepository usersRepository) {
        this.transactionrepository = transactionRepository;
        this.usersRepository = usersRepository;
        transactionRepository.findAll();
    }
    @GetMapping("/Transaction")
    List<Transaction> all3(){
        return transactionrepository.findAll();
    }
    //TODO: mplichta Fix naming of endpoints
    @GetMapping("/Transaction/{id}")
    List<Transaction> TransactionById(@PathVariable int id){
        return transactionrepository.findById(id);
    }
    @GetMapping("/Transaction/{sender}")
    List<Transaction> TransactionBySender(@PathVariable Users sender){
        return transactionrepository.findBySender(sender);
    }
    @GetMapping("/Transaction/{receiver}")
    List<Transaction> TransactionByReceiver(@PathVariable Users receiver){
        return transactionrepository.findByReceiver(receiver);
    }

    @GetMapping("/Transaction/{amount}")
    List<Transaction> TransactionByAmount(@PathVariable double amount){
        return transactionrepository.findByAmount(amount);
    }

    @PostMapping("/addTransaction")
    public ResponseEntity<String> addTransaction(@RequestBody UserTransactionRequest utRequest){
        Users sender = usersRepository.findByName(utRequest.sender);
        Users receiver = usersRepository.findByName(utRequest.receiver);

        Transaction transaction = new Transaction(sender,receiver,utRequest.amount, LocalDateTime.now());
        transactionrepository.save(transaction);

        return new ResponseEntity<String>("Record saved successfully", HttpStatus.CREATED);
    }
}

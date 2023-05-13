package server.requests;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionRepository transactionrepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionrepository = transactionRepository;
        transactionRepository.findAll();
    }
    @GetMapping("/Transaction")
    List<Transaction> all3(){
        return transactionrepository.findAll();
    }
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
}

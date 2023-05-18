package server.app.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.app.requests.UserTransactionRequest;
import server.app.Users.Users;
import server.app.Users.UsersRepository;
import server.app.requests.removeTransactionRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

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

    @GetMapping("/Transaction/bySender/{sender}")
    List<Transaction> TransactionBySender(@PathVariable String sender){
        Users sendingUser = usersRepository.findByName(sender);
        List<Transaction> result = new ArrayList<>();
        for(Transaction x : transactionrepository.findAll()){
            if(x.getSender() == sendingUser){
                result.add(x);
            }
        }
        return result;
    }
    @GetMapping("/Transaction/byReceiver/{receiver}")
    List<Transaction> TransactionByReceiver(@PathVariable String receiver){
        Users receivingUser = usersRepository.findByName(receiver);
        List<Transaction> result = new ArrayList<>();
        for(Transaction x : transactionrepository.findAll()){
            if(x.getReceiver() == receivingUser){
                result.add(x);
            }
        }
        return result;
    }

    @PostMapping("/addTransaction")
    public ResponseEntity<String> addTransaction(@RequestBody UserTransactionRequest utRequest){
        Users sender = usersRepository.findByName(utRequest.sender);
        Users receiver = usersRepository.findByName(utRequest.receiver);

        Transaction transaction = new Transaction(sender,receiver,utRequest.amount, LocalDate.now());
        transactionrepository.save(transaction);
        TransactionGraph trGraph = new TransactionGraph((ArrayList<Transaction>) transactionrepository.findAll(), (ArrayList<Users>) usersRepository.findAll());
        TransactionGraph.setListOfTransactions((ArrayList<Transaction>) transactionrepository.findAll());
        TransactionGraph.setUsers((ArrayList<Users>) usersRepository.findAll());
        //transactionrepository.saveAll(TransactionGraph.getListOfTransactions());
        ArrayList<Transaction> listOfTransactions2 = TransactionGraph.simplify();
        //System.out.println(listOfTransactions2 + "addtransaction");
        transactionrepository.deleteAll(transactionrepository.findAll());
        transactionrepository.saveAll(listOfTransactions2);

        return new ResponseEntity<String>("Record saved successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/removeTransaction")
    Transaction removeTransaction(@RequestBody removeTransactionRequest rtRequest){
        Users sender = usersRepository.findByName(rtRequest.sender);
        Users receiver = usersRepository.findByName(rtRequest.receiver);
        for(Transaction x: transactionrepository.findAll()){
            if(x.getSender() == sender && x.getReceiver() == receiver){
                transactionrepository.delete(x);
                return x;
            }
        }
        return null;
    }

}

package server.app.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.app.requests.UserTransactionRequest;
import server.app.Users.Users;
import server.app.Users.UsersRepository;
import server.app.requests.getUserRequest;
import server.app.requests.removeTransactionRequest;
import java.math.BigDecimal;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @GetMapping("/Transaction/bySender/{sender}")
    List<Transaction> TransactionBySender(@RequestBody getUserRequest urRequest){
        Users sendingUser = usersRepository.findByName(urRequest.username);
        if(!sendingUser.getToken().equals(urRequest.token)){
            return null;
        }
        List<Transaction> result = new ArrayList<>();
        for(Transaction x : transactionrepository.findAll()){
            if(x.getSender() == sendingUser){
                result.add(x);
            }
        }
        return result;
    }
    @GetMapping("/Transaction/byReceiver/{receiver}")
    List<Transaction> TransactionByReceiver(@RequestBody getUserRequest urRequest){
        Users receivingUser = usersRepository.findByName(urRequest.username);
        if(!receivingUser.getToken().equals(urRequest.token)){
            return null;
        }
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
        BigDecimal amount = new BigDecimal(utRequest.amount);
        if(!sender.getToken().equals(utRequest.token) && !receiver.getToken().equals(utRequest.token))
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        if(validateUserInput(utRequest.receiver)){
            Transaction transaction = new Transaction(sender,receiver,amount, LocalDate.now());
            transactionrepository.save(transaction);
            TransactionGraph.users=((ArrayList<Users>) usersRepository.findAll());
            TransactionGraph.simplify();
            transactionrepository.deleteAll(transactionrepository.findAll());
            transactionrepository.saveAll(TransactionGraph.simiplifiedList);

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectMapper objectMapper2 = new ObjectMapper();
            List<Transaction> transactions;
            List<Users> users;
            transactions = transactionrepository.findAll();
            users = usersRepository.findAll();
            try {
                objectMapper.writeValue(new File("requests/src/main/resources/transactionBackup.json"), transactions);
                objectMapper2.writeValue(new File("requests/src/main/resources/usersBackup.json"), users);
            }catch (IOException e){
                e.printStackTrace();
            }
        return new ResponseEntity<>("Record saved successfully", HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/removeTransaction")
    Transaction removeTransaction(@RequestBody removeTransactionRequest rtRequest){
        Users sender = usersRepository.findByName(rtRequest.sender);
        Users receiver = usersRepository.findByName(rtRequest.receiver);
        if(!sender.getToken().equals(rtRequest.token) && !receiver.getToken().equals(rtRequest.token))
            return new Transaction();
        if(validateUserInput(rtRequest.receiver)) {
            for (Transaction x : transactionrepository.findAll()) {
                if (x.getSender() == sender && x.getReceiver() == receiver) {
                    transactionrepository.delete(x);
                    return x;
                }
            }
        }

        return null;
    }

    /**
     * method used to validate the User's input: username or password
     * accepts only characters of English alphabet, digits and a dot
     * @param userInput the user input
     * @return false if the given input contains an illegal character, true otherwise
     */
    public boolean validateUserInput(String userInput){
        return userInput.matches("^[a-zA-Z0-9]+$");
    }

}

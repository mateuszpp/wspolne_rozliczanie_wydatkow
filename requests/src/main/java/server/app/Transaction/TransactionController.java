package server.app.Transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.app.Users.Users;
import server.app.Users.UsersRepository;
import server.app.requests.UserTransactionRequest;
import server.app.requests.getUserRequest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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

    /**
     *
     * @return list of transactions
     */
    @GetMapping("/Transaction")
    ResponseEntity<List<Transaction>> all3(){
        return new ResponseEntity<>(transactionrepository.findAll(),HttpStatus.OK);
    }


    @GetMapping("/Transaction/bySender/{sender}")
    ResponseEntity<List<Transaction>> TransactionBySender(@PathVariable(value="sender") String senderName,
                                                          @RequestHeader(value="token") String token){
        Users sendingUser = usersRepository.findByName(senderName);
        if(!sendingUser.getToken().equals(token)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Transaction> result = new ArrayList<>();
        for(Transaction x : transactionrepository.findAll()){
            if(x.getSender() == sendingUser){
                result.add(x);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     *
     * @param urRequest request body of getUserRequest, needed username and token
     * @return list of transactions in which the name of user was involved as Receiver
     */
    @GetMapping("/Transaction/byReceiver/{receiver}")
    ResponseEntity<List<Transaction>> TransactionByReceiver(@PathVariable(value="receiver") String receiverName,
                                                            @RequestHeader(value="token") String token){
        Users receivingUser = usersRepository.findByName(receiverName);
        if(!receivingUser.getToken().equals(token)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Transaction> result = new ArrayList<>();
        for(Transaction x : transactionrepository.findAll()){
            if(x.getReceiver() == receivingUser){
                result.add(x);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     *method responsible for adding new transaction to the transactiongraph, automatically the balance of all users and list of transactions is
     * being simplified, data is also serialised during the process into 2 files : transactionBackup and usersBackup in json format
     * @param utRequest request body of UserTransactionRequest, needed String sender, String receiver, double amount, String token
     * @return message whether record saved successful or request was invalid
     */
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

    @DeleteMapping("/removeTransaction/{sender}/{receiver}")
    ResponseEntity<Transaction> removeTransaction(@PathVariable(value="sender") String senderName,
                                                  @PathVariable(value="receiver") String receiverName,
                                                  @RequestHeader("token") String token){
        Users sender = usersRepository.findByName(senderName);
        Users receiver = usersRepository.findByName(receiverName);
        if(!sender.getToken().equals(token) && !receiver.getToken().equals(token))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(validateUserInput(receiverName)) {
            for (Transaction x : transactionrepository.findAll()) {
                if (x.getSender() == sender && x.getReceiver() == receiver) {
                    transactionrepository.delete(x);
                    TransactionGraph.listOfTransactions= (ArrayList<Transaction>) transactionrepository.findAll();
                    return new ResponseEntity<>(x, HttpStatus.OK);
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

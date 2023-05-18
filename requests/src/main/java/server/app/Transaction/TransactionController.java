package server.app.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.app.requests.UserTransactionRequest;
import server.app.Users.Users;
import server.app.Users.UsersRepository;
import server.app.requests.removeTransactionRequest;
import java.math.BigDecimal;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        BigDecimal amount = new BigDecimal(utRequest.amount);
        if(validateUserInput(utRequest.receiver)){
            Transaction transaction = new Transaction(sender,receiver,amount, LocalDate.now());
            transactionrepository.save(transaction);
            TransactionGraph.users=((ArrayList<Users>) usersRepository.findAll());
            TransactionGraph.simplify();
            transactionrepository.deleteAll(transactionrepository.findAll());
            transactionrepository.saveAll(TransactionGraph.simiplifiedList);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Transaction> transactions = new ArrayList<>();
            transactions = transactionrepository.findAll();
            try {
                objectMapper.writeValue(new File("requests/src/main/resources/transactionBackup.json"), transactions);
            }catch (IOException e){
                e.printStackTrace();
            }
        return new ResponseEntity<String>("Record saved successfully", HttpStatus.CREATED);
        }
        else return new ResponseEntity<String>("Invalid request", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/removeTransaction")
    Transaction removeTransaction(@RequestBody removeTransactionRequest rtRequest){
        Users sender = usersRepository.findByName(rtRequest.sender);
        Users receiver = usersRepository.findByName(rtRequest.receiver);
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
     * method used to validate the input of transaction amount
     * accepts only digits and a dot
     * @param transactionInput
     * @return false if the transaction input contains an illegal character, true otherwise
     *//*
    public boolean validateTransactionInput(String transactionInput){
        String input = transactionInput;
        Pattern pattern = Pattern.compile("^[0-9.]");
        Matcher matcher = pattern.matcher(input);
        boolean valid = matcher.find();
        return !valid;
    }
    */
    /**
     * method used to validate the User's input: username or password
     * accepts only characters of English alphabet, digits and a dot
     * @param userInput
     * @return false if the given input contains an illegal character, true otherwise
     */
    public boolean validateUserInput(String userInput){
        String input = userInput;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.]");
        Matcher matcher = pattern.matcher(input);
        boolean valid = matcher.find();
        return !valid;
    }

}

package server.app.Users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import server.app.Transaction.Transaction;
import server.app.Transaction.TransactionRepository;
import server.app.requests.UserRegisterRequest;
import server.app.requests.changePasswordRequest;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

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

    @PostMapping("/users/remove/{name}")
    void removeUser(@PathVariable String name){
        usersRepository.delete(userByName(name));

        ObjectMapper objectMapper = new ObjectMapper();
        List<Users> users = new ArrayList<>();
        users = usersRepository.findAll();
        try {
            objectMapper.writeValue(new File("requests/src/main/resources/usersBackup.json"),users);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @GetMapping("/transactions")
    List<Transaction> allTransactions(){return transactionRepository.findAll();}


    @PostMapping("/users/register")
    Users register(@RequestBody UserRegisterRequest urRequest) throws NoSuchAlgorithmException {
        Users user = new Users();
        if(validateUserInput(urRequest.username) && validateUserInput(urRequest.password)) {
            user = new Users(urRequest.username, urRequest.password);
            usersRepository.save(user);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<Users> users = new ArrayList<>();
        users = usersRepository.findAll();
        try {
            objectMapper.writeValue(new File("requests/src/main/resources/usersBackup.json"),users);
        }catch (IOException e){
            e.printStackTrace();
        }
        return user;
    }

    @PostMapping("/users/login")
    Users login(@RequestBody UserRegisterRequest urRequest) throws NoSuchAlgorithmException{
        Users user = new Users();
        if(validateUserInput(urRequest.username) && validateUserInput(urRequest.password)){
            user = usersRepository.Login(urRequest.username, Users.hashPassword(urRequest.password));
        }
            return user;
    }

    @PatchMapping("/users/changePassword")
    Users changePassword(@RequestBody changePasswordRequest cPRequest) throws NoSuchAlgorithmException{
        Users user = new Users();
        if(validateUserInput(cPRequest.username) && validateUserInput(cPRequest.currentPassword) && validateUserInput(cPRequest.newPassword)){
        user = usersRepository.Login(cPRequest.username, Users.hashPassword(cPRequest.currentPassword));
        //user.setHashedPasswd(cPRequest.newPassword);
        //user = usersRepository.Login(cPRequest.username, Users.hashPassword(cPRequest.newPassword));
        usersRepository.getReferenceById(user.getId()).setHashedPasswd(cPRequest.newPassword);
        usersRepository.save(user);
        }
        return user;
    }
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

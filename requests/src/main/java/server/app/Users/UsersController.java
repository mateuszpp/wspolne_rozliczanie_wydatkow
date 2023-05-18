package server.app.Users;

import org.springframework.web.bind.annotation.*;
import server.app.Transaction.Transaction;
import server.app.Transaction.TransactionRepository;
import server.app.requests.UserRegisterRequest;
import server.app.requests.changePasswordRequest;

import java.security.NoSuchAlgorithmException;
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

    @PostMapping("/users/remove/{name}")
    void removeUser(@PathVariable String name){
        usersRepository.delete(userByName(name));
    }


    @GetMapping("/transactions")
    List<Transaction> allTransactions(){return transactionRepository.findAll();}

    @PostMapping("/users/register")
    void register(@RequestBody UserRegisterRequest urRequest) throws NoSuchAlgorithmException {
        Users user = new Users(urRequest.username, urRequest.password);
        usersRepository.save(user);
    }

    @PostMapping("/users/login")
    Users login(@RequestBody UserRegisterRequest urRequest) throws NoSuchAlgorithmException{
        return usersRepository.Login(urRequest.username, Users.hashPassword(urRequest.password));
    }

    @PatchMapping("/users/changePassword")
    Users changePassword(@RequestBody changePasswordRequest cPRequest) throws NoSuchAlgorithmException{
        Users user = usersRepository.Login(cPRequest.username, Users.hashPassword(cPRequest.currentPassword));
        //user.setHashedPasswd(cPRequest.newPassword);
        //user = usersRepository.Login(cPRequest.username, Users.hashPassword(cPRequest.newPassword));
        usersRepository.getReferenceById(user.getId()).setHashedPasswd(cPRequest.newPassword);
        usersRepository.save(user);
        return user;
    }
}

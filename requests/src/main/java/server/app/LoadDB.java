package server.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.app.Transaction.Transaction;
import server.app.Transaction.TransactionGraph;
import server.app.Transaction.TransactionRepository;
import server.app.Users.Users;
import server.app.Users.UsersRepository;
import java.math.BigDecimal;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

@Configuration
public class LoadDB {

    private static final Logger log = LoggerFactory.getLogger(LoadDB.class);

    public void loader(UsersRepository repository, TransactionRepository transactionrepository){
        File jasonUsersFile = new File("requests/src/main/resources/usersBackup.json");
        File jasonTransaction = new File("requests/src/main/resources/transactionBackup.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Users> usersList = objectMapper.readValue(jasonUsersFile, new TypeReference<List<Users>>() {
            });
            List<Transaction> transactionList = objectMapper.readValue(jasonTransaction, new TypeReference<List<Transaction>>() {
            });
            for (Users user : usersList) {
                log.info("Preloading" + repository.save(user));
            }
            for (Transaction transaction : transactionList) {
                log.info("Preloading " + transactionrepository.save(transaction));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    CommandLineRunner initDatabase(UsersRepository repository, TransactionRepository transactionrepository) {
        return args -> {
            loader(repository,transactionrepository);
            Users user1 = new Users( "mati","wat");
            Users user2 = new Users( "kuba","wat");
            Users user3 = new Users( "rafał","wat");
            Users user4 = new Users( "Maja","wat");
            Users user5 = new Users( "karol","wat");
            Users user6 = new Users( "maks","wat");
            log.info("Preloading " + repository.save(user1));
            log.info("Preloading " + repository.save(user2));
            log.info("Preloading " + repository.save(user3));
            log.info("Preloading " + repository.save(user4));
            log.info("Preloading " + repository.save(user5));
            log.info("Preloading " + repository.save(user6));
            Transaction transaction1 =new Transaction(user1 ,user3 ,750.0 , LocalDate.now());
            Transaction transaction2 =new Transaction(user1, user2, 200.0, LocalDate.of(2021, 4, 2));
            Transaction transaction3 =new Transaction(user3, user2, 250.0, LocalDate.of(2021, 4, 2));
            Transaction transaction4 =new Transaction(user1, user2, 100.0, LocalDate.of(2021, 4, 2));
            Transaction transaction5 =new Transaction(user2, user3, 1000.0, LocalDate.of(2021, 4, 2));
            Transaction transaction6 =new Transaction(user4, user5, 600.0, LocalDate.of(2021, 4, 4));
            Transaction transaction7 =new Transaction(user4, user6, 800.0, LocalDate.of(2021, 4, 7));
            Transaction transaction8 =new Transaction(user4, user5, 1200.0, LocalDate.of(2021, 4, 7));
            Transaction transaction9 =new Transaction(user6, user5, 200.0, LocalDate.of(2021, 4, 7));
            Transaction transaction10 =new Transaction(user4, user6, 400.0, LocalDate.of(2021, 4, 7));
            TransactionGraph transactionGraph = new TransactionGraph((ArrayList<Transaction>) transactionrepository.findAll(),(ArrayList<Users>) repository.findAll());
            log.info("Preloading " + transactionrepository.save(transaction1));
            log.info("Preloading " + transactionrepository.save(transaction2));
            log.info("Preloading " + transactionrepository.save(transaction3));
            log.info("Preloading " + transactionrepository.save(transaction4));
            log.info("Preloading " + transactionrepository.save(transaction5));
            log.info("Preloading " + transactionrepository.save(transaction6));
            log.info("Preloading " + transactionrepository.save(transaction7));
            log.info("Preloading " + transactionrepository.save(transaction8));
            log.info("Preloading " + transactionrepository.save(transaction9));
            log.info("Preloading " + transactionrepository.save(transaction10));
            repository.saveAll(TransactionGraph.getUsers());
        };
    }


}

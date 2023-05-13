package server.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import server.app.Transaction.Transaction;
import server.app.Transaction.TransactionRepository;
import server.app.Users.Users;
import server.app.Users.UsersRepository;

import java.time.LocalDateTime;

@Configuration
public class LoadDB {

    private static final Logger log = LoggerFactory.getLogger(LoadDB.class);

    @Bean
    CommandLineRunner initDatabase(UsersRepository repository, TransactionRepository transactionrepository) {

        return args -> {
            Users kuba = new Users("szwedson", "asdfasdf",30.0);
            Users kuba2 = new Users("szwedson2", "asdfasdfad",30.0);
            log.info("Preloading " + repository.save(kuba));
            log.info("Preloading " + repository.save(kuba2));
            log.info("Preloading " + transactionrepository.save(new Transaction(kuba ,kuba2 ,30.0 , LocalDateTime.now() )));
        };
    }


}

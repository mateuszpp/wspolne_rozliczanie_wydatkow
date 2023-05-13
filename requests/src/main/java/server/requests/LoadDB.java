package server.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDB {

    private static final Logger log = LoggerFactory.getLogger(LoadDB.class);

    @Bean
    CommandLineRunner initUsersDatabase(UsersRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Users("szwedson", "asdfasdf")));
            log.info("Preloading " + repository.save(new Users("szwedson2", "asdfasdf2")));
        };
    }

}

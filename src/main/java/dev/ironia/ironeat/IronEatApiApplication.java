package dev.ironia.ironeat;

import dev.ironia.ironeat.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class IronEatApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IronEatApiApplication.class, args);
    }

}

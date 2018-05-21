package pl.lelental.musiclibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Paweł Lelental
 **/
@EnableJpaRepositories("pl.lelental.musiclibrary")
@EntityScan("pl.lelental.musiclibrary.model")
@ComponentScan(basePackages = {"pl.lelental.musiclibrary"})
@SpringBootApplication
public class TestConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(TestConfiguration.class, args);
    }
}

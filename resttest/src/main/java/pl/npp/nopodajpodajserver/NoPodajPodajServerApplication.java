package pl.npp.nopodajpodajserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Paweł Lelental
 **/
@SpringBootApplication
@EnableJpaRepositories("pl.npp.nopodajpodajserver")
@EntityScan("pl.npp.nopodajpodajserver.model")
@ComponentScan(basePackages = { "pl.npp.nopodajpodajserver" })
public class NoPodajPodajServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoPodajPodajServerApplication.class, args);
    }
}

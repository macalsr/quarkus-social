package io.github.mariacsr.quarkussocial.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnvLoader {

    @PostConstruct
    void loadEnv() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(
                entry -> System.setProperty(entry.getKey(),entry.getValue())
        );
    }
}

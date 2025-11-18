package com.example.Vehiculos.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    public EnvConfig() {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMissing()
                .load();
        
        // Load MONGODB_URI from .env or environment
        String mongoUri = dotenv.get("MONGODB_URI");
        if (mongoUri != null) {
            System.setProperty("spring.data.mongodb.uri", mongoUri);
        }
    }
}

package nl.tudelft.sem.group20.authenticationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableAutoConfiguration
@EnableDiscoveryClient
public class AuthenticationServer {

    /** Starts application. **/
    public static void main(String[] args) {
        // Will configure using accounts-server.yml
        System.setProperty("spring.config.name", "authenticationserver.properties");

        SpringApplication.run(AuthenticationServer.class, args);
    }
}
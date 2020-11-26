package nl.tudelft.sem.group20.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class EurekaServer {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "eureka");
        SpringApplication.run(EurekaServer.class, args);
    }
}


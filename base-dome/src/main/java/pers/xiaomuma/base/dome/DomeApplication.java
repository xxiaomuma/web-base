package pers.xiaomuma.base.dome;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pers.xiaomuma.base.web.EnableBaseConfiguration;

@EnableBaseConfiguration
@SpringBootApplication
public class DomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomeApplication.class, args);
    }
}

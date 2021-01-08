package pers.xiaomuma.web.dome;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pers.xiaomuma.base.db.config.BaseDbConfiguration;

@Import(BaseDbConfiguration.class)
@SpringBootApplication
public class DomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomeApplication.class, args);
    }
}

package uk.krupa.me.kruton.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "uk.krupa.me.kruton.bot"})
public class Application {
    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
}

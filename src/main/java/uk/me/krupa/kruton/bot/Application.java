package uk.me.krupa.kruton.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "uk.me.krupa.kruton.bot"})
public class Application {
    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
}

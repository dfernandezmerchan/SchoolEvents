package es.safareyes.schoolevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchoolEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolEventsApplication.class, args);
    }

}


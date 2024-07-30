package com.example.notificationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class NotificationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServerApplication.class, args);
    }

}

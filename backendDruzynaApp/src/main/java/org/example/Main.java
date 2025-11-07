package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Main.class, args);

        System.out.println("\n=== ZAREJESTROWANE KONTROLERY ===");
        String[] beanNames = ctx.getBeanNamesForAnnotation(RestController.class);
        Arrays.stream(beanNames).forEach(name ->
                System.out.println("✅ " + name + " -> " + ctx.getBean(name).getClass().getName())
        );
        System.out.println("=================================\n");
    }
}
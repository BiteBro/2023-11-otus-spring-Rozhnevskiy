package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.printf("Чтобы перейти на стартовую страницу сайта открывай: %n%s%n",
                "http://localhost:8080/api/author");
        System.out.printf("Чтобы перейти на стартовую страницу сайта открывай: %n%s%n",
                "http://localhost:8080/api/genre");
        System.out.printf("Чтобы перейти на стартовую страницу сайта открывай: %n%s%n",
                "http://localhost:8080/api/book");
        System.out.printf("Чтобы перейти на стартовую страницу сайта открывай: %n%s%n",
                "http://localhost:8080/api/comment");
    }

}

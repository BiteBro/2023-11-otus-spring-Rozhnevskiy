package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.printf("Чтобы перейти на стартовую страницу сайта открывай: %n%s%n",
				"http://localhost:8080");
		/*System.out.printf("Чтобы перейти на страницу сайта с Книгами открывай: %n%s%n",
				"http://localhost:8080/book");
		System.out.printf("Чтобы перейти на страницу сайта с Авторами открывай: %n%s%n",
				"http://localhost:8080/author");
		System.out.printf("Чтобы перейти на страницу сайта с Жанрами открывай: %n%s%n",
				"http://localhost:8080/genre");
		System.out.printf("Чтобы перейти на страницу сайта с Комментариями открывай: %n%s%n",
				"http://localhost:8080/comment");*/
	}

}

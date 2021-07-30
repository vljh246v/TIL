package com.demo.reactiveweb.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class StudyApplication {

	public static void main(String[] args) {
//		SpringApplication.run(StudyApplication.class, args);

		Flux<String> flux = Flux.just("A");
		Flux<String> flux2 = flux.map(s -> "foo" + s);
		flux2.subscribe(System.out::println);
	}

}

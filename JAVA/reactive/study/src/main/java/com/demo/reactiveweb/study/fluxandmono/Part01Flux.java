package com.demo.reactiveweb.study.fluxandmono;
//generic imports to help with simpler IDEs (ie tech.io)
import java.util.*;
import java.util.function.*;
import java.time.*;

import reactor.core.publisher.Flux;

/**
 * Learn how to create Flux instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html">Flux Javadoc</a>
 */
public class Part01Flux {

	public static void main(String[] args) throws InterruptedException {
//		Flux.fromIterable(List.of("foo", "bar"))
//				.doOnNext(System.out::println)
//				.map(String::toUpperCase)
//				.subscribe(System.out::println);

//		Flux.error(new IllegalStateException())
//				.doOnError(System.out::println)
//				.subscribe(System.out::println);

		Flux.interval(Duration.ofMillis(100))
				.take(10)
				.subscribe(System.out::println);

		System.out.println("!!!");

		Thread.sleep(5000);

	}

//========================================================================================

	// TODO Return an empty Flux
	Flux<String> emptyFlux() {
		return Flux.empty();
	}

//========================================================================================

	// TODO Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
	Flux<String> fooBarFluxFromValues() {
		return Flux.just("foo", "bar");
	}

//========================================================================================

	// TODO Create a Flux from a List that contains 2 values "foo" and "bar"
	Flux<String> fooBarFluxFromList() {
		List<String> items = new ArrayList<>();
		items.add("foo");
		items.add("bar");
		return Flux.fromIterable(items);
	}

//========================================================================================

	// TODO Create a Flux that emits an IllegalStateException
	Flux<String> errorFlux() {
		return Flux.error(new IllegalStateException());
	}

//========================================================================================

		// TODO Create a Flux that emits increasing values from 0 to 9 each 100ms
	Flux<Long> counter() {
		return Flux.interval(Duration.ofMillis(100))
				.take(10);
	}

}

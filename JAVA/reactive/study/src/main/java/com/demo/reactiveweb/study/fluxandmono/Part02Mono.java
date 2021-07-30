package com.demo.reactiveweb.study.fluxandmono;

//generic imports to help with simpler IDEs (ie tech.io)
import java.util.*;
import java.util.function.*;
import java.time.*;

import reactor.core.publisher.Mono;

/**
 * Learn how to create Mono instances.
 *
 * @author Sebastien Deleuze
 * @see <a href="https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html">Mono Javadoc</a>
 */
public class Part02Mono {

	public static void main(String[] args) {
		Mono.just(1)
				.map(integer -> "foo" + integer)
				.or(Mono.delay(Duration.ofMillis(100)).map(l -> l + ""))
				.subscribe(System.out::println);
	}

//========================================================================================

	// TODO Return an empty Mono
	Mono<String> emptyMono() {
		return Mono.empty();
	}

//========================================================================================

	// TODO Return a Mono that never emits any signal
	Mono<String> monoWithNoSignal() {
		return Mono.never();
	}

//========================================================================================

	// TODO Return a Mono that contains a "foo" value
	Mono<String> fooMono() {
		return Mono.just("foo");
	}

//========================================================================================

	// TODO Create a Mono that emits an IllegalStateException
	Mono<String> errorMono() {
		return Mono.error(new IllegalStateException());
	}

}

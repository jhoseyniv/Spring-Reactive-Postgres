package reactive140110.demo;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
class DemoApplicationTests {

	@Test
	public void simple() {
		Publisher<Integer> rangeOfIntegers = Flux.range(0, 10);
		StepVerifier.create(rangeOfIntegers).expectNextCount(10).verifyComplete();

		Flux<String> letters = Flux.just("A", "B", "C");
		StepVerifier.create(letters).expectNext("A", "B", "C").verifyComplete();

		long now = System.currentTimeMillis();
		Mono<Date> greetingMono = Mono.just(new Date(now));
		StepVerifier.create(greetingMono).expectNext(new Date(now)).verifyComplete();


	}
	@Test
	public void createAFlux_just() {
		Flux<String> fruitFlux = Flux
				.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

		fruitFlux.subscribe(
				f -> System.out.println("Here's some fruit: " + f)
		);
//		StepVerifier.create(fruitFlux)
//				.expectNext("Apple")
//				.expectNext("Orange")
//				.expectNext("Grape")
//				.expectNext("Banana")
//				.expectNext("Strawberry")
//				.verifyComplete();
	}
	@Test
	public void createAFlux_fromArray() {
		String[] fruits = new String[] {
				"Apple", "Orange", "Grape", "Banana", "Strawberry" };
		List<String> fruits2 = Arrays.asList("Apple", "Orange", "Grape", "Banana", "Strawberry");
		Flux<String> stringFlux = Flux.fromArray(fruits);
		StepVerifier.create(stringFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}
	@Test
	public void createAFlux_fromStream() {
		Stream<String> fruitStream =
				Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
		Flux<String> fruitFlux = Flux.fromStream(fruitStream);
		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	@Test
	public void createAFlux_range() {
		Flux<Integer> intervalFlux =
				Flux.range(1, 5);
		StepVerifier.create(intervalFlux)
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.expectNext(4)
				.expectNext(5)
				.verifyComplete();
	}
	@Test
	public void createAFlux_interval() {
		Flux<Long> intervalFlux =
				Flux.interval(Duration.ofSeconds(1))
						.take(6);
		StepVerifier.create(intervalFlux)
				.expectNext(0L)
				.expectNext(1L)
				.expectNext(2L)
				.expectNext(3L)
				.expectNext(4L)
				.expectNext(5L)
				.verifyComplete();
	}

	@Test
	public void mergeFluxes() {
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa")
				.delayElements(Duration.ofMillis(500));
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples")
				.delaySubscription(Duration.ofMillis(250))
				.delayElements(Duration.ofMillis(500));
		Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);
		StepVerifier.create(mergedFlux)
				.expectNext("Garfield")
				.expectNext("Lasagna")
				.expectNext("Kojak")
				.expectNext("Lollipops")
				.expectNext("Barbossa")
				.expectNext("Apples")
				.verifyComplete();
	}

	@Test
	public void zipFluxes() {
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
		Flux<Tuple2<String, String>> zippedFlux =
				Flux.zip(characterFlux, foodFlux);
		StepVerifier.create(zippedFlux)
				.expectNextMatches(p ->
						p.getT1().equals("Garfield") &&
								p.getT2().equals("Lasagna"))
				.expectNextMatches(p ->
						p.getT1().equals("Kojak") &&
								p.getT2().equals("Lollipops"))
				.expectNextMatches(p ->
						p.getT1().equals("Barbossa") &&
								p.getT2().equals("Apples"))
				.verifyComplete();
	}

}

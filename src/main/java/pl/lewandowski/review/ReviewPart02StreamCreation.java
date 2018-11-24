package pl.lewandowski.review;

import pl.lewandowski.presto.products.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.time.Duration.ofMinutes;
import static java.time.Duration.ofNanos;

public class ReviewPart02StreamCreation {

    /**
     * Create Mono from maybe null element
     */
    public static Mono<Product> b01FromMaybeNull(Product product) {
		return null;
    }

    /**
     * Create Mono from Optional Element
     */
    public static Mono<Product> b02FromOptional(Optional<Product> product) {
		return null;
    }

    /**
     * Create Mono from Callable
     */
    public static Mono<Product> b03FromCallable(Callable<Product> productList) {
		return null;
    }

    /**
     * Create Flux from Stream
     */
    public static Flux<Product> b04FromStream(Stream<Product> productStream) {
		return null;
    }

    /**
     * Create Flux from Array
     */
    public static Flux<Product> b05FromArray(Product[] productArray) {
		return null;
    }

    /**
     * Create Flux from List
     */
    public static Flux<Product> b06FromList(List<Product> productList) {
		return null;
    }

    /**
     * Create Flux from productA and productB
     */
    public static Flux<Product> b07FromObjects(Product productA, Product productB) {
		return null;
    }

    /**
     * Create Flux of integers from 0 to 9
     */
    public static Flux<Integer> b08Ranged() {
		return null;
    }

    /**
     * This flux is producing events. Take first 5 events.
     */
    public static Flux<Integer> b09Take(Flux<Integer> numbers) {
		return null;
    }

    /**
     * Emit events:
     * * Wait 1 minute
     * * Emit 0L
     * * Wait 1 minute
     * * Emit 1L
     * * Wait 1 minute
     * * Emit 2L
     * * Emit complete
     */
    public static Flux<Long> b10Interval() {
		return null;
    }

    /**
     * Emit events:
     * * Emit 0L
     * * Wait 1 minute
     * * Emit 1L
     * * Wait 1 minute
     * * Emit 2L
     * * Emit complete
     */
    public static Flux<Long> b11IntervalDelay() {
		return null;
    }

    /**
     * You are given a supplier which emit some number <b>i</b>.<br>
     * If the number is positive you should emit and complete <b>2 * i</b><br>
     * If the number is zero you should emit error.<br>
     * If the number is negative you should return absolute value from this number.<br><br>
     *
     * Remember that getting value from supplier is expensive and you should call it only when there will be subscription to your Publisher.<br><br>
     * 
     * HINT: use <i></i>Flux.generate</i> method.
     */
    public static Flux<Long> b12Generate(Supplier<Long> expensiveSupplier) {
		return null;
}

    /**
     * You have one Product. Multiply it by creating unlimited product stream.
     */
    public static Flux<Product> b13Repeat(Mono<Product> product){
		return null;
    }
}

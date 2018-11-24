package pl.lewandowski.review;

import pl.lewandowski.presto.products.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReviewPart01CoreSignals {

    /**
     * Create Mono publisher which emits complete.
     */
    public static Mono<String> a01None() {
		return null;
    }

    /**
     * Create Flux publisher which emits complete.
     */
    public static Flux<String> a02None() {
		return null;
    }

    /**
     * Create Mono publisher which emits error from IllegalAccessException type.
     */
    public static Mono<String> a03Problem() {
		return null;
    }

    /**
     * Create Flux publisher with emits error from from IllegalAccessException type.
     */
    public static Flux<String> a04Problem() {
		return null;
    }

    /**
     * Create Mono publisher which will never emit anything
     */
    public static Mono<String> a05Null() {
		return null;
    }

    /**
     * Create Flux publisher which will never emit anything
     */
    public static Flux<String> a06Null() {
		return null;
    }

    /**
     * Create Mono publisher which emits one element of type Product with id ABC.
     */
    public static Mono<Product> a07One() {
		return null;
    }

    /**
     * Create Flux publisher which emits one element of type Product with id 1.
     */
    public static Flux<Product> a08One() {
		return null;
    }

}

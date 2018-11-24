package pl.lewandowski.review;

import pl.lewandowski.presto.products.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Flux.fromStream;
import static reactor.core.publisher.Mono.just;

public class ReviewPart04Operators {

    /**
     * Mono can emmit one item. You can filter unwanted value, leaving the Publisher empty.  This function should filter only Products that starts with "A" letter.
     */
    public Mono<Product> d01Filter(Mono<Product> theProduct) {
		return null;
    }

    /**
     * Flux can emmit multiple items. You can filter unwanted values.  This function should filter only Products that starts with "A" letter.
     */
    public Flux<Product> d02Filter(Flux<Product> theProduct) {
		return null;
    }

    /**
     * Sometimes your predicate function is some other reactive publisher. Use {@link #checkMyProduct(Product) checkMyProduct} function, to filter values from incoming stream.
     */
    public Mono<Product> d03Filter(Mono<Product> theProduct) {
		return null;
    }

    /**
     * You can translate events from one type to anther. Convert Products to Product Id stream.
     */
    public Mono<String> d04Map(Mono<Product> theProduct) {
		return null;
    }

    /**
     * Sometimes your mapping function is some other reactive publisher. Use {@link #getProductSize(Product) getProductSize} function, to map values from incoming stream.
     */
    public Mono<Integer> d05FlatMap(Mono<Product> theProduct) {
		return null;
    }

    /**
     * Flux can be translated the same way as mono. Convert Products to Products Id stream.
     */
    public Flux<String> d06Map(Flux<Product> theProduct) {
		return null;
    }

    /**
     * Sometimes your mapping function is some other reactive publisher. Use {@link #getProductSize(Product) getProductSize} function, to map values from incoming stream.
     */
    public Flux<Integer> d07FlatMap(Flux<Product> theProduct) {
		return null;
    }

    /**
     * You can convert Mono with one event to flux with multiple events. Convert incoming Mono Publisher, to Flux Publisher using {@link #getProductAllParts(Product) getProductAllParts} function.
     */
    public Flux<Character> d08FlatMap(Mono<Product> theProduct) {
		return null;
    }

    private Mono<Boolean> checkMyProduct(Product product) {
        //This may be call to external service
        return just(product.getId().contains("A"));
    }

    private Mono<Integer> getProductSize(Product product) {
        //This may be call to external service
        return just(product.getId().length());
    }

    private Flux<Character> getProductAllParts(Product product) {
        //This may be call to external service
        return fromStream(product.getId().chars().mapToObj(c -> (char)c));
    }
}

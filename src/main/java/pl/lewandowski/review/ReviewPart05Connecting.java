package pl.lewandowski.review;

import pl.lewandowski.presto.products.Offer;
import pl.lewandowski.presto.products.OffersForProduct;
import pl.lewandowski.presto.products.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.function.BiFunction;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static reactor.core.publisher.Flux.fromStream;

public class ReviewPart05Connecting {

    /**
     * You have two web services. First to get a product. Second to get offer.<br>
     * You have to call them simultaneously and combine the results. Use zip function to do that. <br><br>
     * <p>
     * When you implement it. Run this @see <a href="pl.lewandowski.review.ReviewPart05ConnectingTest#02_mono_zip_see_times()">test</a> and observe the logs.
     *
     * <ul>
     * <li> What can you tell about the execution times. Were the publisher executed simultaneously?
     * <li> What can you tell about the execution threads?
     * </ul>
     */
    public Mono<Tuple2<Product, Offer>> e01_combine(Mono<Product> product, Mono<Offer> offer) {
        return product.zipWith(offer); // return null;
    }

    /**
     * Implement @see <a href="pl.lewandowski.review.ReviewPart05Connecting#productPrice(pl.lewandowski.presto.products.Product, pl.lewandowski.presto.products.Offer)">productPrice</a> function and use it with zip function.
     */
    public Mono<OffersForProduct> e02_combine_with_function(Mono<Product> product, Mono<Offer> offer) {
        return product.zipWith(offer, this::productPrice); // return null;
    }

    /**
     * You have two web services that receives offer. Using concat create one flux.
     */
    public Flux<Offer> e03_concat(Mono<Offer> offerOne, Mono<Offer> offerTwo) {
        return offerOne.concatWith(offerTwo); // return null;
    }

    /**
     * What is the disadvantage of concat method?
     * Run <a href="pl.lewandowski.review.ReviewPart05ConnectingTest#04_concat_observe_times()">04_concat_observe_times</a> test and observe logs.
     *
     * Find better way to two get two streams simultaneously and merge them.
     */
    public Flux<Offer> e04_merge(Mono<Offer> offerOne, Mono<Offer> offerTwo) {
        return offerOne.mergeWith(offerTwo); // return null;
    }

    /**
     * Let's use merge and zip together. First you will have to merge all the offers and zip them together.
     *
     * Hint: Use <a href="reactor.core.publisher.Flux#collectList()" to convert Flux to Mono>collectList()</a> to convert Flux to Mono.
     */
    public Mono<OffersForProduct> e05_zip_merge(Mono<Product> product, Mono<Offer> offerOne, Mono<Offer> offerTwo) {
        return offerOne // return null;
                .mergeWith(offerTwo) // DELETE ME
                .collectList() // DELETE ME
                .zipWith(product, (offers, product1) -> new OffersForProduct(product1,offers)); // DELETE ME
    }

    private OffersForProduct productPrice(Product product, Offer offer) {
        return new OffersForProduct(product, singletonList(offer)); // return null;
    }
}


package pl.lewandowski.review.util

import org.javamoney.moneta.Money
import pl.lewandowski.presto.products.Offer
import pl.lewandowski.presto.products.Product
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.time.Duration
import java.util.function.Predicate

class ReviewSpecification extends Specification {

    protected final Product A_PRODUCT = new Product("A")

    public static final Product B_PRODUCT = new Product("B")

    protected final Product ABC_PRODUCT = new Product("ABC")

    protected final Offer A_OFFER = new Offer(1, Money.of(1.0, "PLN"))

    protected final Offer B_OFFER = new Offer(1, Money.of(2.0, "PLN"))

    protected final Predicate<Product> ABC_PREDICATE = { product -> (ABC_PRODUCT == product) } as Predicate<Product>

    protected final <T> Mono<T> delay(Mono<T> object, Long millis) {
        return object.doOnNext({ it -> log.info("Production started. Object is: " + it) }).delayElement(Duration.ofMillis(millis)).doOnNext({ it -> log.info("Production completed. Object is: " + it) });
    }
}

package pl.lewandowski.review;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.javamoney.moneta.Money;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import pl.lewandowski.presto.products.Offer;
import pl.lewandowski.presto.products.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.javamoney.moneta.Money.of;
import static org.javamoney.moneta.Money.zero;
import static pl.lewandowski.presto.products.Offer.DEFAULT_CURRENCY;

public class ReviewPart03Subscribe {

    /**
     * In order to consume items from subscriber, you can use consumer, to consume those items. Subscribe to given Subscriber with provided consumer.
     */
    public static void c01Subscribe(Mono<Product> product, Consumer<Product> consumer) {
        product.subscribe(consumer); // return;
    }

    /**
     * Consumer is some kind of wrapper on Subscriber that only reacts to onNext method. You can implement your own Subscribers. You will have to implement 4 methods:
     * <ul>
     * <li>onSubscribe</li> This is called when we subscribe to our method. Object of type subscription is created. We have to request number of items we want to get. See <a href="reactor.core.publisher.LambdaMonoSubscriber#onSubscribe(org.reactivestreams.Subscription)">example subscriber</a> for tips how to implement it.
     * <li>onNext</li> When new event that is emitted, this method is called.
     * <li>onError</li> When error, this method is called.
     * <li>onComplete</li> When the stream ends, this method is called.
     * </ul>
     * <p>
     * You received publisher of type Product. You goal is to convert it to Optional with productId, using your own Subscription.
     * If Subscriber does not emit any event, you have to emmit "Fallback" productId.
     * If Subscriber emits error, you have to return empty Optional.
     */
    public static Optional<String> c02Subscribe(Mono<Product> product) {
        MyMonoSubscriber actual = new MyMonoSubscriber(); // return null;
        product.subscribe(actual);  // DELETE ME
        return actual.getProductId(); // DELETE ME
    }

    /**
     * You can subscribe to Flux and Mono in the same way.
     * Now using only subscribe with custom implementation (or subscribe with consumer for next element and error element), calculate the average price of incoming offers.
     * Error in the stream should be ignored.
     *
     * Do not use any other reactor operator. What is the disadvantage of not using reactive operators?
     */
    public static Money c03Subscribe(Flux<Offer> offers) {
        List<Money> prices = new ArrayList<>();
        offers.subscribe(offer -> prices.add(offer.getPrice()), throwable -> {}); // return null;
        return prices.stream().reduce(new MoneyAverage(), MoneyAverage::accept, MoneyAverage::combine).average(); // DELETE ME
    }

    /**
     * Regardless of the number of emitted events in result Flux, you should return Mono with:
     * <ul>
     * <li>COMPLETE</li> if the input stream complete successfully.
     * <li>ERROR</li> if the input stream ends with error.
     * </ul>
     */
    public static Mono<Void> c04Then(Flux<Product> result) {
        return result.then(); // return null;
    }

    /**
     * Regardless of the number of emitted events in productsFromServiceA and its status, you should wait for productsFromServiceB to be completed. Based on its status you should emit:
     * <ul>
     * <li>COMPLETE</li> if the input stream complete successfully.
     * <li>ERROR</li> if the input stream ends with error.
     * </ul>
     */
    public static Mono<Void> c05ThenEmpty(Flux<Product> productsFromServiceA, Flux<Product> productsFromServiceB) {
        return productsFromServiceA.thenEmpty(productsFromServiceB.then()); // return null;
    }

    /**
     * Wait for productsFromServiceA stream to be completed, and then return productsFromServiceB Publisher.
     */
    public static Mono<String> c06ThenOtherFlux(Flux<Product> productsFromServiceA, Mono<String> productsFromServiceB) {
        return productsFromServiceA.then(productsFromServiceB); // return null;
    }

    /**
     * Wait for productsFromServiceA stream to be completed, and then return productsFromServiceB Publisher.
     */
    public static Flux<String> c07ThenNonEmptyFlux(Flux<Product> productsFromServiceA, Flux<String> productsFromServiceB) {
        return productsFromServiceA.thenMany(productsFromServiceB); // return null;
    }

    public static Long c08BlockTail(Flux<Long> numbers) {
        return numbers.blockLast(); // return null;
    }

    public static Long c08BlockHead(Flux<Long> numbers) {
        return numbers.blockFirst(); // return null;
    }

    public static Long c08BlockSingle(Mono<Long> number) {
        return number.block(); // return null;
    }

    public static Mono<Product> c09Timeout(Mono<Product> number) {
        return number.timeout(Duration.ofHours(1)); // return null;
    }
}
// DELETE ME
// DELETE ME
@Value// DELETE ME
@AllArgsConstructor// DELETE ME
class MoneyAverage {// DELETE ME
    private final Money total;// DELETE ME
    private final int count;// DELETE ME
// DELETE ME
    MoneyAverage() {// DELETE ME
        this.total = zero(DEFAULT_CURRENCY);// DELETE ME
        this.count = 0;// DELETE ME
    }// DELETE ME
// DELETE ME
    Money average() {// DELETE ME
        return count > 0 ? total.divide(count): zero(DEFAULT_CURRENCY);// DELETE ME
    }// DELETE ME
// DELETE ME
    MoneyAverage accept(Money that) {// DELETE ME
        return new MoneyAverage(total.add(that), count + 1);// DELETE ME
    }// DELETE ME
// DELETE ME
    MoneyAverage combine(MoneyAverage that) {// DELETE ME
        return new MoneyAverage(total.add(that.getTotal()), count + that.count);// DELETE ME
    }// DELETE ME
}// DELETE ME
// DELETE ME
class MyMonoSubscriber implements Subscriber<Product> {  // DELETE ME
    // DELETE ME
    private String productId = null; // DELETE ME
    // DELETE ME
    // DELETE ME
    @Override // DELETE ME
    public void onSubscribe(Subscription s) { // DELETE ME
        s.request(Long.MAX_VALUE); // DELETE ME
    } // DELETE ME
    // DELETE ME
    // DELETE ME
    @Override // DELETE ME
    public void onNext(Product product) { // DELETE ME
        productId = product.getId(); // DELETE ME
    } // DELETE ME
    // DELETE ME
    // DELETE ME
    @Override // DELETE ME
    public void onError(Throwable t) { // DELETE ME
    } // DELETE ME
    // DELETE ME
    // DELETE ME
    @Override // DELETE ME
    public void onComplete() { // DELETE ME
        if (productId == null) {// DELETE ME
            productId = "Fallback"; // DELETE ME
        }// DELETE ME
        // DELETE ME
    } // DELETE ME
    // DELETE ME
    // DELETE ME
    Optional<String> getProductId() { // DELETE ME
        return Optional.ofNullable(productId); // DELETE ME
    } // DELETE ME
}// DELETE ME

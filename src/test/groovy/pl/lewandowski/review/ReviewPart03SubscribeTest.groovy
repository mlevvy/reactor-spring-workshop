package pl.lewandowski.review

import org.javamoney.moneta.Money
import pl.lewandowski.presto.products.Offer
import pl.lewandowski.presto.products.Product
import pl.lewandowski.review.util.ReviewSpecification
import pl.lewandowski.review.util.VerifyingConsumer
import pl.lewandowski.review.util.VerifyingSupplier
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import spock.lang.Specification

import java.time.Duration
import java.util.concurrent.TimeoutException
import java.util.function.Consumer

import static pl.lewandowski.presto.products.Offer.DEFAULT_CURRENCY
import static reactor.test.StepVerifier.create

class ReviewPart03SubscribeTest extends ReviewSpecification {

    def "01 subscribe from mono"() {
        given:
            Product product = new Product("ABC")
            Mono<Product> mono = Mono.just(product)
            VerifyingConsumer consumer = new VerifyingConsumer();
        when:
            ReviewPart03Subscribe.c01Subscribe(mono, consumer)
        then:
            consumer.consumed() == [product]
    }

    def "02 custom mono subscription"() {
        expect:
            ReviewPart03Subscribe.c02Subscribe(Mono.just(new Product("ABC"))).get() == "ABC"
            !ReviewPart03Subscribe.c02Subscribe(Mono.error(new IllegalAccessException())).isPresent()
            ReviewPart03Subscribe.c02Subscribe(Mono.empty()).get() == "Fallback"
    }

    def "03 custom flux subscription"() {
        expect:
            ReviewPart03Subscribe.c03Subscribe(Flux.just(offerWithPrice(1.2))).getNumberStripped() == 1.2
            ReviewPart03Subscribe.c03Subscribe(Flux.just(offerWithPrice(1.0), offerWithPrice(2.0))).getNumberStripped() == 1.5
            ReviewPart03Subscribe.c03Subscribe(Flux.just(offerWithPrice(1.0), offerWithPrice(2.0)).concatWith(Flux.error(new IllegalAccessException()))).getNumberStripped() == 1.5
    }

    def "04 then"() {
        expect:
            ReviewPart03Subscribe.c04Then(Flux.empty()) != null
            create(ReviewPart03Subscribe.c04Then(Flux.empty())).expectComplete()
            create(ReviewPart03Subscribe.c04Then(Flux.just(new Product("ABC")))).expectComplete()
            create(ReviewPart03Subscribe.c04Then(Flux.error(new IllegalAccessException()))).expectError(IllegalAccessException)
    }

    def "05 then both complete"() {
        expect:
            ReviewPart03Subscribe.c05ThenEmpty(Flux.empty(), Flux.empty()) != null
            create(ReviewPart03Subscribe.c05ThenEmpty(Flux.empty(), Flux.empty())).expectComplete()
            create(ReviewPart03Subscribe.c05ThenEmpty(Flux.just(new Product("ABC")), Flux.empty())).expectComplete()
            create(ReviewPart03Subscribe.c05ThenEmpty(Flux.error(new IllegalAccessException()), Flux.empty())).expectComplete()
            create(ReviewPart03Subscribe.c05ThenEmpty(Flux.empty(), Flux.error(new IllegalAccessException()))).expectError(IllegalAccessException)
    }

    def "06 then call other mono"() {
        given:
            VerifyingSupplier<Product> supplier = new VerifyingSupplier(new Product("ABC"))
            Flux<Product> input = Flux.generate({ sink -> sink.next(supplier.get()); sink.complete() } as Consumer<SynchronousSink<Product>>)

        when:
            def create = create(ReviewPart03Subscribe.c06ThenOtherFlux(input, Mono.just("200 OK")))

        then:
            create.expectNext("200 OK").verifyComplete()
            supplier.calledAtLeastOnce()
    }

    def "07 then call other flux"() {
        given:
            VerifyingSupplier<Product> supplier = new VerifyingSupplier(new Product("ABC"))
            Flux<Product> input = Flux.generate({ sink -> sink.next(supplier.get()); sink.complete() } as Consumer<SynchronousSink<Product>>)

        when:
            def create = create(ReviewPart03Subscribe.c07ThenNonEmptyFlux(input, Flux.just("200 OK")))

        then:
            create.expectNext("200 OK").verifyComplete()
            supplier.calledAtLeastOnce()
    }

    def "08 block"() {
        expect:
            ReviewPart03Subscribe.c05ThenEmpty(Flux.empty(), Flux.empty()) != null
            ReviewPart03Subscribe.c08BlockSingle(Mono.just(1L)) == 1L
            ReviewPart03Subscribe.c08BlockHead(Flux.just(1L, 2L, 3L)) == 1L
            ReviewPart03Subscribe.c08BlockTail(Flux.just(1L, 2L, 3L)) == 3L
    }

    def "09 timeout"() {
        given:
            VirtualTimeScheduler.getOrSet()

        expect:
            StepVerifier.withVirtualTime({ ReviewPart03Subscribe.c09Timeout(Mono.never()) })
                    .expectSubscription()
                    .thenAwait(Duration.ofHours(1))
                    .expectError(TimeoutException)
        and:
            ReviewPart03Subscribe.c09Timeout(Mono.never()) != null
    }

    private static Offer offerWithPrice(BigDecimal price) {
        new Offer(1, Money.of(price, DEFAULT_CURRENCY))
    }


}

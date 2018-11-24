package pl.lewandowski.review

import pl.lewandowski.presto.products.Product
import pl.lewandowski.review.util.ReviewSpecification
import pl.lewandowski.review.util.VerifyingSupplier
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.test.publisher.PublisherProbe
import reactor.test.scheduler.VirtualTimeScheduler
import spock.lang.Unroll

import java.time.Duration
import java.util.concurrent.Callable
import java.util.function.Predicate

import static java.util.stream.Stream.of
import static reactor.test.StepVerifier.create

class ReviewPart02StreamCreationTest extends ReviewSpecification {

    def "01 maybe null"() {
        expect:
            create(ReviewPart02StreamCreation.b01FromMaybeNull(null)).verifyComplete()
            create(ReviewPart02StreamCreation.b01FromMaybeNull(ABC_PRODUCT)).expectNextMatches(ABC_PREDICATE).verifyComplete()
    }

    def "02 optional"() {
        expect:
            create(ReviewPart02StreamCreation.b02FromOptional(Optional.empty())).verifyComplete()
            create(ReviewPart02StreamCreation.b02FromOptional(null)).verifyComplete()
            create(ReviewPart02StreamCreation.b02FromOptional(Optional.of(ABC_PRODUCT))).expectNextMatches(ABC_PREDICATE).verifyComplete()
    }

    def "03_callable"() {
        expect:
            create(ReviewPart02StreamCreation.b03FromCallable({
                return ABC_PRODUCT
            } as Callable<Product>)).expectNextMatches(ABC_PREDICATE).verifyComplete()
    }

    def "04 stream"() {
        expect:
            create(ReviewPart02StreamCreation.b04FromStream(of((ABC_PRODUCT)))).expectNextMatches(ABC_PREDICATE).verifyComplete()
    }

    def "05 array"() {
        expect:
            create(ReviewPart02StreamCreation.b05FromArray([ABC_PRODUCT] as Product[])).expectNextMatches(ABC_PREDICATE).verifyComplete()
    }

    def "06 list"() {
        expect:
            create(ReviewPart02StreamCreation.b06FromList([ABC_PRODUCT])).expectNextMatches(ABC_PREDICATE).verifyComplete()
    }

    def "07 from two elements"() {
        expect:
            create(ReviewPart02StreamCreation.b07FromObjects(ABC_PRODUCT, ABC_PRODUCT)).expectNextMatches(ABC_PREDICATE).expectNextMatches(ABC_PREDICATE).verifyComplete()
    }

    def "08 elements from 0 to 9"() {
        expect:
            create(ReviewPart02StreamCreation.b08Ranged()).expectNext(*(0..9)).verifyComplete()
    }

    def "09 take"() {
        expect:
            create(ReviewPart02StreamCreation.b09Take(Flux.just(1, 2, 3))).expectNext(1, 2).verifyComplete()
    }

    def "10 interval"() {
        expect:
            VirtualTimeScheduler.getOrSet()

            StepVerifier.withVirtualTime({ ReviewPart02StreamCreation.b10Interval() })
                    .expectSubscription()
                    .thenAwait(Duration.ofMinutes(1))
                    .expectNext(0L)
                    .thenAwait(Duration.ofMinutes(1))
                    .expectNext(1L)
                    .thenAwait(Duration.ofMinutes(1))
                    .expectNext(2L)
                    .verifyComplete()

    }

    def "11 interval without delay"() {
        expect:
            VirtualTimeScheduler.getOrSet()

            StepVerifier.withVirtualTime({ ReviewPart02StreamCreation.b11IntervalDelay() })
                    .expectSubscription()
                    .expectNext(0L)
                    .thenAwait(Duration.ofMinutes(1))
                    .expectNext(1L)
                    .thenAwait(Duration.ofMinutes(1))
                    .expectNext(2L)
                    .verifyComplete()
    }

    @Unroll
    def "12 basic sink "() {
        given:
            VerifyingSupplier positiveSupplier = new VerifyingSupplier(input)

        when:
            Flux<Long> sut = ReviewPart02StreamCreation.b12Generate(positiveSupplier)

        then:
            !positiveSupplier.calledAtLeastOnce()
        and:
            create(sut)
                    .expectNext(output)
                    .verifyComplete()
        and:
            positiveSupplier.calledAtLeastOnce()

        where:
            input | output
            1L    | 2L
            -1L   | 1L
    }

    @Unroll
    def "12 basic sink - corner case"() {
        given:
            VerifyingSupplier supplier = new VerifyingSupplier(0L)

        when:
            Flux<Long> sut = ReviewPart02StreamCreation.b12Generate(supplier)

        then:
            !supplier.calledAtLeastOnce()
        and:
            create(sut)
                    .verifyError(IllegalArgumentException)
        and:
            supplier.calledAtLeastOnce()
    }

    @Unroll
    def "13 creating flux from one mono"() {
        when:
            Flux<Product> sut = ReviewPart02StreamCreation.b13Repeat(Mono.just(ABC_PRODUCT)).take(2)

        then:
            create(sut)
                    .expectNextMatches(ABC_PREDICATE)
                    .expectNextMatches(ABC_PREDICATE)
                    .verifyComplete()
    }

}

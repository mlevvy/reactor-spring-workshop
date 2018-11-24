package pl.lewandowski.review

import groovy.util.logging.Slf4j
import pl.lewandowski.presto.products.Offer
import pl.lewandowski.presto.products.OffersForProduct
import pl.lewandowski.presto.products.Product
import pl.lewandowski.review.util.ReviewSpecification
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import reactor.util.function.Tuple2
import spock.lang.Ignore
import spock.lang.Timeout

import java.util.concurrent.TimeUnit
import java.util.function.Supplier

import static java.time.Duration.ofMillis
import static reactor.core.publisher.Mono.just
import static reactor.test.StepVerifier.create
import static reactor.test.StepVerifier.withVirtualTime

@Slf4j
class ReviewPart05ConnectingTest extends ReviewSpecification {

    ReviewPart05Connecting sut = new ReviewPart05Connecting()

    def "01 mono zip"() {
        given:
            VirtualTimeScheduler.getOrSet()

        and:
            Mono product = delay(just(A_PRODUCT), 500)
            Mono offer = delay(just(A_OFFER), 1000)

        when:
            Mono<Tuple2<Product, Offer>> result = sut.e01_combine(product, offer)

        then:
            withVirtualTime({ result })
                    .expectSubscription()
                    .expectNoEvent(ofMillis(1000))
                    .expectNextMatches({ t -> t.getT1() == A_PRODUCT && t.getT2() == A_OFFER })
                    .verifyComplete()
    }

    @Ignore("This test does not use Virtual Time. It should be used to observe logs.")
    def "01 mono zip observe times"() {
        given:
            Mono product = delay(just(A_PRODUCT), 500)
            Mono offer = delay(just(A_OFFER), 1000)

        when:
            Mono<Tuple2<Product, Offer>> result = sut.e01_combine(product, offer).doOnNext({ result -> log.info("Result is: " + result) })

        then:
            create(result)
                    .expectNextMatches({ t -> t.getT1() == A_PRODUCT && t.getT2() == A_OFFER })
                    .verifyComplete()
    }

    def "02 mono zip with function"() {
        when:
            Mono<OffersForProduct> result = sut.e02_combine_with_function(just(A_PRODUCT), just(A_OFFER))

        then:
            create(result)
                    .expectNext(new OffersForProduct(A_PRODUCT, [A_OFFER]))
                    .verifyComplete()
    }

    def '03 concat test'() {
        when:
            Flux<Offer> concat = sut.e03_concat(just(A_OFFER), just(B_OFFER))

        then:
            create(concat)
                    .expectNext(A_OFFER)
                    .expectNext(B_OFFER)
                    .verifyComplete()
    }

    @Ignore("This test does not use Virtual Time. It should be used to observe logs.")
    def "04 concat observe times"() {
        given:
            Mono delayed_e = delay(just(B_OFFER), 500)
            Mono delayed_d = delay(just(A_OFFER), 1000)

        when:
            def result = sut.e03_concat(delayed_e, delayed_d)

        then:
            create(result)
                    .expectNext(B_OFFER)
                    .expectNext(A_OFFER)
                    .verifyComplete()
    }

    @Timeout(value = 900, unit = TimeUnit.MILLISECONDS)
    def "04 merge test"() {
        given:
            VirtualTimeScheduler.getOrSet()

        and:
            Mono delayed_e = delay(just(B_OFFER), 500)
            Mono delayed_d = delay(just(A_OFFER), 1000)

        when:
            def result = sut.e04_merge(delayed_d, delayed_e)

        then:
            withVirtualTime({ result })
                    .expectSubscription()
                    .thenAwait(ofMillis(1000))
                    .expectNext(B_OFFER)
                    .expectNext(A_OFFER)
                    .verifyComplete()
    }

    def "05 zip merge test"() {
        when:
            Mono<OffersForProduct> merge = sut.e05_zip_merge(just(A_PRODUCT), just(A_OFFER), just(B_OFFER))

        then:
            create(merge)
                    .expectNext((new OffersForProduct(A_PRODUCT, [A_OFFER, B_OFFER])))
                    .verifyComplete()
    }

}

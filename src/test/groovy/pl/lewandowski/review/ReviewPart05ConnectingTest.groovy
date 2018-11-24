package pl.lewandowski.review

import groovy.util.logging.Slf4j
import pl.lewandowski.presto.products.OffersForProduct
import pl.lewandowski.review.util.ReviewSpecification
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import spock.lang.Ignore
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

import static java.time.Duration.ofMillis
import static reactor.core.publisher.Mono.just
import static reactor.test.StepVerifier.withVirtualTime

@Slf4j
class ReviewPart05ConnectingTest extends ReviewSpecification {

    ReviewPart05Connecting sut = new ReviewPart05Connecting()

    def "01 mono zip"() {
        expect:
            VirtualTimeScheduler.getOrSet()

            Mono product = delay(just(A_PRODUCT), 500)
            Mono offer = delay(just(A_OFFER), 1000)

            withVirtualTime({ sut.e01_combine(product, offer) })
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

        expect:
            StepVerifier
                    .create(sut.e01_combine(product, offer).doOnNext({ result -> log.info("Result is: " + result) }))
                    .expectNextMatches({ t -> t.getT1() == A_PRODUCT && t.getT2() == A_OFFER })
                    .verifyComplete()
    }

    def "02 mono zip with function"() {
        expect:
            StepVerifier
                    .create(sut.e02_combine_with_function(just(A_PRODUCT), just(A_OFFER)))
                    .expectNext(new OffersForProduct(A_PRODUCT, [A_OFFER]))
                    .verifyComplete()
    }

    def '03 concat test'() {
        expect:
            StepVerifier.create(sut.e03_concat(just(A_OFFER), just(B_OFFER)))
                    .expectNext(A_OFFER)
                    .expectNext(B_OFFER)
                    .verifyComplete()
    }

    @Ignore("This test does not use Virtual Time. It should be used to observe logs.")
    def "04 concat observe times"() {
        given:
            Mono delayed_e = delay(just(B_OFFER), 500)
            Mono delayed_d = delay(just(A_OFFER), 1000)

        expect:
            StepVerifier.create(sut.e03_concat(delayed_e, delayed_d))
                    .expectNext(B_OFFER)
                    .expectNext(A_OFFER)
                    .verifyComplete()
    }

    @Timeout(value = 900, unit = TimeUnit.MILLISECONDS)
    def "04 merge test"() {
        given:
            VirtualTimeScheduler.getOrSet()

            Mono delayed_e = delay(just(B_OFFER), 500)
            Mono delayed_d = delay(just(A_OFFER), 1000)

        expect:
            withVirtualTime({ sut.e04_merge(delayed_d, delayed_e) })
                    .expectSubscription()
                    .thenAwait(ofMillis(1000))
                    .expectNext(B_OFFER)
                    .expectNext(A_OFFER)
                    .verifyComplete()
    }

    def "05 zip merge test"() {
        expect:
            StepVerifier.create(sut.e05_zip_merge(just(A_PRODUCT),just(A_OFFER), just(B_OFFER)))
                    .expectNext((new OffersForProduct(A_PRODUCT, [A_OFFER, B_OFFER])))
                    .verifyComplete()
    }

}

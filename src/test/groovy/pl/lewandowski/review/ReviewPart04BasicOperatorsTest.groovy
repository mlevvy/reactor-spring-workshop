package pl.lewandowski.review

import pl.lewandowski.presto.products.Product
import pl.lewandowski.review.util.ReviewSpecification
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification

import static reactor.test.StepVerifier.create

class ReviewPart04BasicOperatorsTest extends ReviewSpecification {

    public static final Product A = new Product("A")

    public static final Product B = new Product("B")

    public static final Product ABC = new Product("ABC")

    ReviewPart04Operators sut = new ReviewPart04Operators();

    def "01 mono filter"() {
        expect:
            create(sut.d01Filter(Mono.just(A))).expectNextMatches({ p -> p.getId() == "A" }).verifyComplete()
            create(sut.d01Filter(Mono.just(B))).verifyComplete()
    }

    def "02 flux filter"() {
        expect:
            create(sut.d02Filter(Flux.just(A))).expectNextMatches({ p -> p.getId() == "A" }).verifyComplete()
            create(sut.d02Filter(Flux.just(B))).verifyComplete()
    }

    def "03 filter with external service call"() {
        expect:
            create(sut.d03Filter(Mono.just(A))).expectNextMatches({ p -> p.getId() == "A" }).verifyComplete()
            create(sut.d03Filter(Mono.just(B))).verifyComplete()
    }

    def "04 mono map"() {
        expect:
            create(sut.d04Map(Mono.just(A))).expectNext("A").verifyComplete()
    }

    def "05 mono flatmap"() {
        expect:
            create(sut.d05FlatMap(Mono.just(A))).expectNext(1).verifyComplete()
            create(sut.d05FlatMap(Mono.just(ABC))).expectNext(3).verifyComplete()
    }

    def "06 flux map"() {
        expect:
            create(sut.d06Map(Flux.just(A, B, ABC))).expectNext("A", "B", "ABC").verifyComplete()
    }

    def "07 flux flat map"() {
        expect:
            create(sut.d07FlatMap(Flux.just(A, B, ABC))).expectNext(1, 1, 3).verifyComplete()
    }

    def "07 mono flat map"() {
        expect:
            create(sut.d08FlatMap(Mono.just(ABC))).expectNext('A' as Character, 'B' as Character, 'C' as Character).verifyComplete()
    }

}

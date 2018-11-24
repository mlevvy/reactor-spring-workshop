package pl.lewandowski.review

import pl.lewandowski.presto.products.Product
import pl.lewandowski.review.util.ReviewSpecification
import spock.lang.Specification

import java.util.function.Predicate

import static java.time.Duration.ofMillis
import static reactor.test.StepVerifier.create

class ReviewPart01CoreSignalsTest extends ReviewSpecification {

    def "01 none mono"(){
        expect:
            create(ReviewPart01CoreSignals.a01None()).verifyComplete();
    }

    def "02 none flux"(){
        expect:
            create(ReviewPart01CoreSignals.a02None()).verifyComplete();
    }

    def "03 problem mono"(){
        expect:
            create(ReviewPart01CoreSignals.a03Problem()).verifyError(IllegalAccessException);
    }

    def "04 problem flux"(){
        expect:
            create(ReviewPart01CoreSignals.a04Problem()).verifyError(IllegalAccessException);
    }

    def "05 none mono"(){
        expect:
            create(ReviewPart01CoreSignals.a05Null()).expectSubscription().expectNoEvent(ofMillis(500)).thenCancel().verify();
    }

    def "06 none flux"(){
        expect:
            create(ReviewPart01CoreSignals.a06Null()).expectSubscription().expectNoEvent(ofMillis(500)).thenCancel().verify();

    }

    def "07 product mono"(){
        expect:
            create(ReviewPart01CoreSignals.a07One()).expectNextMatches(ABC_PREDICATE).verifyComplete()
    }

    def "08 product flux"(){
        expect:
            create(ReviewPart01CoreSignals.a08One()).expectNextMatches(ABC_PREDICATE).verifyComplete()

    }
}

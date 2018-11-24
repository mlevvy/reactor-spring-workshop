package pl.lewandowski.presto.products

import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

class ApiSpec extends Specification {

    def "should return offer for some product"() {
        given:
            PricingService pricingService = Stub(PricingService)
            ProductService productService = Stub(ProductService)
            WarehouseService warehouseService = Stub(WarehouseService)
        and:
            Api sut = new Api(pricingService, warehouseService, productService)
        when:
            Mono<Offer> offer = sut.getOffer(1L)
        then:
            StepVerifier
                    .create(offer)
                    .expectNextMatches(new OfferPredicateBuilder()
                        .containsPriceInPLN(new BigDecimal(1))
                        .containsItems(1L)
                        .predicate())
                    .verifyComplete()

    }
}

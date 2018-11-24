package pl.lewandowski.presto.products

import org.javamoney.moneta.Money

import java.util.function.Predicate

class OfferPredicateBuilder {

    private Money expectedPrice

    private Long expectedItems

    def containsPriceInPLN(BigDecimal price){
        expectedPrice = Money.of(price, "PLN")
        return this;
    }

    def containsItems(Long items){
        expectedItems = items
        return this;
    }

    Predicate<Offer> predicate(){
        new Predicate<Offer>() {
            @Override
            boolean test(Offer testedOffer) {
                return testedOffer.items == expectedItems && testedOffer.price == expectedPrice
            }
        }
    }
}

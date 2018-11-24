package pl.lewandowski.presto.products;

import lombok.AllArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class Api {

    private final PricingService pricingService;

    private final WarehouseService warehouseService;

    private final ProductService productService;

    public Mono<Offer> getOffer(Long productId) {
        return null;
    }
}

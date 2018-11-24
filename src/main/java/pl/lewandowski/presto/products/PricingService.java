package pl.lewandowski.presto.products;

import org.javamoney.moneta.Money;
import reactor.core.publisher.Flux;
import java.util.Map;

public interface PricingService {
    Flux<Map<Client, Money>> getPriceOptions();
}

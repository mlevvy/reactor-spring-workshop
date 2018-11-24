package pl.lewandowski.presto.products;

import reactor.core.publisher.Mono;

public interface WarehouseService {
    Mono<Long> getAvailableItems();
}

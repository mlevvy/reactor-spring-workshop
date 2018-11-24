package pl.lewandowski.presto.products;

import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> findOne(Long id);
}

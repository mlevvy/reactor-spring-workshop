package pl.lewandowski.presto.products;

import lombok.Value;

import java.util.List;

@Value
public class OffersForProduct {
    private final Product product;
    private List<Offer> offerList;
}

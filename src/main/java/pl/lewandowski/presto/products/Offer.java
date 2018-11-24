package pl.lewandowski.presto.products;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

@Value
@AllArgsConstructor
public class Offer {

    public static final CurrencyUnit DEFAULT_CURRENCY = Monetary.getCurrency("PLN");

    Long items;

    Money price;
}

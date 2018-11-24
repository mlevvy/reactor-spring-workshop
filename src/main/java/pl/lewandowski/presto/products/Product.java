package pl.lewandowski.presto.products;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class Product {

    @Id
    private String id;

}

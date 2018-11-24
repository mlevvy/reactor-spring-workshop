package pl.lewandowski.review.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class VerifyingConsumer<T> implements Consumer<T> {

    private List<T> items = new ArrayList<>();


    public List<T> consumed() {
        return items;
    }

    @Override
    public void accept(T item) {
        items.add(item);
    }
}

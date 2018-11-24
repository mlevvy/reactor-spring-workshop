package pl.lewandowski.review.util;

import java.util.function.Supplier;

//TODO Refactor me to PublisherProbe
public class VerifyingSupplier<T> implements Supplier<T> {

    private boolean supplied = false;

    private T number;

    public VerifyingSupplier(T number) {
        this.number = number;
    }

    public boolean calledAtLeastOnce() {
        return supplied;
    }

    @Override
    public T get() {
        supplied = true;
        return number;
    }
}

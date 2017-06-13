package org.cactoos.list;

import java.util.Iterator;

/**
 * Limited iterable.
 *
 * <p>This is a view of an existing iterable containing the given number of its
 * first elements.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @param <T> Element type
 * @since 0.4
 */
public final class LimitedIterable<T> implements Iterable<T> {
  
    private final Iterable<T> iterable;
    private final int limit;

    /**
     * Ctor.
     *
     * @param iterable the underlying iterable
     * @param limit the requested number of elements
     */
    public LimitedIterable(Iterable<T> iterable, int limit) {
        this.iterable = iterable;
        this.limit = limit;
    }

    @Override
    public Iterator<T> iterator() {
        return new LimitedIterator<>(iterable.iterator(), limit);
    }
}

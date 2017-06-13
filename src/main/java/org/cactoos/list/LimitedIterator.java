package org.cactoos.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Limited iterator.
 *
 * <p>This is a decorator over an existing iterator. Returns elements of the
 * original iterator, until either the requested number of items have been
 * returned or the underlying iterator has been exhausted.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @param <T> Element type
 * @since 0.4
 */
public final class LimitedIterator<T> implements Iterator<T> {
  
    private final Iterator<T> iterator;
    private final int limit;
    private int consumed;

    /**
     * Ctor.
     *
     * @param iterator the underlying iterator
     * @param limit the requested number of elements
     */
    public LimitedIterator(Iterator<T> iterator, int limit) {
        this.iterator = iterator;
        this.limit = limit;
        this.consumed = 0;
    }

    @Override
    public boolean hasNext() {
        return (consumed < limit) && iterator.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements.");
        }
        ++consumed;
        return iterator.next();
    }
}

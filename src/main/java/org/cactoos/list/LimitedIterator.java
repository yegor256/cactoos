package org.cactoos.list;

import java.util.Iterator;

/**
 * Limited iterator.
 *
 * <p>This is a decorator over an existing iterator. Returns elements of the
 * original iterator, until either the requested number of items have been
 * returned or the underlying iterator has been exhausted.</p>
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @param <T> Element type
 * @since 0.4
 */
public class LimitedIterator<T> implements Iterator<T> {
  
    private final Iterator<T> iterator;
    private final int limitSize;
    private int consumedSize;

    /**
     * Ctor.
     *
     * @param iterator the underlying iterator
     * @param limitSize the requested number of elements
     */
    public LimitedIterator(Iterator<T> iterator, int limitSize) {
        if (limitSize < 0) {
            throw new IllegalArgumentException(
                "Expected limitSize >= 0, got [" + limitSize + "]."
            );
        }
        this.iterator = iterator;
        this.limitSize = limitSize;
        this.consumedSize = 0;
    }

    @Override
    public boolean hasNext() {
        return (consumedSize < limitSize) && iterator.hasNext();
    }

    @Override
    public T next() {
        ++consumedSize;
        return iterator.next();
    }
}

package org.cactoos.list;

import java.util.Iterator;

/**
 * Limited iterable.
 *
 * <p>This is a view of an existing iterable containing the given number of its
 * first elements.</p>
 *
 * @author Dusan Rychnovsky (dusan.rychnovsky@gmail.com)
 * @param <T> Element type
 * @since 0.4
 */
public class LimitedIterable<T> implements Iterable<T> {
  
    private final Iterable<T> iterable;
    private final int limitSize;

    /**
     * Ctor.
     *
     * @param iterable the underlying iterable
     * @param limitSize the requested number of elements
     */
    public LimitedIterable(Iterable<T> iterable, int limitSize) {
        if (limitSize < 0) {
            throw new IllegalArgumentException(
                "Expected limitSize >= 0, got [" + limitSize + "]."
            );
        }
        this.iterable = iterable;
        this.limitSize = limitSize;
    }

    @Override
    public Iterator<T> iterator() {
        return new LimitedIterator<>(iterable.iterator(), limitSize);
    }
}

package org.cactoos.list;

import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A decorator of {@link ListIterator} that is immutable and tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 */
public final class ListIteratorNoNulls<T> implements ListIterator<T> {

    /**
     * ListIterator
     */
    private final ListIterator<T> listIterator;

    /**
     * Position
     */
    private final AtomicLong pos;

    /**
     * Ctor.
     * @param src list iterator.
     */
    public ListIteratorNoNulls(final ListIterator<T> src){
       this.listIterator = new ListIteratorOf<>(src);
       this.pos = new AtomicLong();
    }

    @Override
    public boolean hasNext() {
        return this.listIterator.hasNext();
    }

    @Override
    public T next() {
        final T next = this.listIterator.next();
        if (next == null) {
            throw new IllegalStateException(
                    new UncheckedText(
                            new FormattedText(
                                    "Item #%d of #listIterator() is NULL",
                                    this.pos.get()
                            )
                    ).asString()
            );
        }
        this.pos.incrementAndGet();
        return next;
    }

    @Override
    public boolean hasPrevious() {
        return this.listIterator.hasPrevious();
    }

    @Override
    public T previous() {
        final T next = this.listIterator.previous();
        if (next == null) {
            throw new IllegalStateException(
                    new UncheckedText(
                            new FormattedText(
                                    "Item #%d of #listIterator() is NULL",
                                    this.pos.get()
                            )
                    ).asString()
            );
        }
        this.pos.decrementAndGet();
        return next;
    }

    @Override
    public int nextIndex() {
        return this.listIterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.listIterator.previousIndex();
    }

    @Override
    public void remove() {
        this.listIterator.remove();
    }

    @Override
    public void set(T item) {
        this.listIterator.set(item);
    }

    @Override
    public void add(T item) {
        this.listIterator.add(item);
    }

}

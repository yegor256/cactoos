/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.SumOfInt;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * {@link List} envelope that doesn't allow mutations.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 1.16
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 * @todo #898:30min Replace all the Collections.unmodifiableList
 *  with the {@link org.cactoos.list.Immutable} from the cactoos codebase.
 *  That should be done because Elegant Object principles are against static methods.
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming",
        "PMD.AvoidDuplicateLiterals"
    }
)
public final class Immutable<T> implements List<T> {

    /**
     * Encapsulated list.
     */
    private final List<T> list;

    /**
     * Ctor.
     * @param items Source array
     */
    @SafeVarargs
    public Immutable(final T... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param src Source collection
     */
    public Immutable(final Collection<T> src) {
        this(new IterableOf<>(src.iterator()));
    }

    /**
     * Ctor.
     * @param src Source list
     */
    public Immutable(final List<T> src) {
        this(new IterableOf<>(src.iterator()));
    }

    /**
     * Ctor.
     * @param src Source iterable
     */
    public Immutable(final Iterable<T> src) {
        this(() -> {
            final List<T> copy = new ArrayList<>(1);
            for (final T item : src) {
                copy.add(item);
            }
            return copy;
        });
    }

    /**
     * Ctor.
     * @param slr The scalar
     */
    public Immutable(final Scalar<List<T>> slr) {
        this.list = new Unchecked<>(slr).value();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(final Object item) {
        return this.list.contains(item);
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <X> X[] toArray(final X[] array) {
        return this.list.toArray(array);
    }

    @Override
    public boolean add(final T item) {
        throw new UnsupportedOperationException(
            "#add(T): the list is read-only"
        );
    }

    @Override
    public boolean remove(final Object item) {
        throw new UnsupportedOperationException(
            "#remove(Object): the list is read-only"
        );
    }

    @Override
    public boolean containsAll(final Collection<?> items) {
        return this.list.containsAll(items);
    }

    @Override
    public boolean addAll(final Collection<? extends T> items) {
        throw new UnsupportedOperationException(
            "#addAll(Collection): the list is read-only"
        );
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends T> items) {
        throw new UnsupportedOperationException(
            "#addAll(int, Collection): the list is read-only"
        );
    }

    @Override
    public boolean removeAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#removeAll(): the list is read-only"
        );
    }

    @Override
    public boolean retainAll(final Collection<?> items) {
        throw new UnsupportedOperationException(
            "#retainAll(): the list is read-only"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the list is read-only"
        );
    }

    @Override
    public T get(final int index) {
        return this.list.get(index);
    }

    @Override
    public T set(final int index, final T item) {
        throw new UnsupportedOperationException(
            "#set(): the list is read-only"
        );
    }

    @Override
    public void add(final int index, final T item) {
        throw new UnsupportedOperationException(
            "#add(int, T): the list is read-only"
        );
    }

    @Override
    public T remove(final int index) {
        throw new UnsupportedOperationException(
            "#remove(int): the list is read-only"
        );
    }

    @Override
    public int indexOf(final Object item) {
        return this.list.indexOf(item);
    }

    @Override
    public int lastIndexOf(final Object item) {
        return this.list.lastIndexOf(item);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ImmutableListIterator<>(
            this.list.listIterator()
        );
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ImmutableListIterator<>(
            this.list.listIterator(index)
        );
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return new Immutable<>(
            this.list.subList(start, end)
        );
    }

    @Override
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EQ_UNUSUAL")
    public boolean equals(final Object other) {
        return new Unchecked<>(
            new Or(
                () -> other == this,
                new And(
                    () -> other != null,
                    () -> List.class.isAssignableFrom(other.getClass()),
                    () -> {
                        final List<?> compared = (List<?>) other;
                        final Iterator<?> iterator = compared.iterator();
                        return new Unchecked<>(
                            new And(
                                (T input) -> input.equals(iterator.next()),
                                this
                            )
                        ).value();
                    }
                )
            )
        ).value();
    }

    // @checkstyle MagicNumberCheck (30 lines)
    @Override
    public int hashCode() {
        return new Unchecked<>(
            new Folded<>(
                42,
                (hash, entry) -> new SumOfInt(
                    () -> 37 * hash,
                    entry::hashCode
                ).value(),
                this
            )
        ).value();
    }

    @Override
    public String toString() {
        return new UncheckedText(new TextOf(this)).asString();
    }
}

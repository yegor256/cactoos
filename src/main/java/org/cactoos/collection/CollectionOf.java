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
package org.cactoos.collection;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.And;
import org.cactoos.scalar.HashCode;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.Unchecked;

/**
 * Iterable as {@link Collection}.
 *
 * <p>This class should be used very carefully. You must understand that
 * it will fetch the entire content of the encapsulated {@link Iterable} on each
 * method call. It doesn't cache the data anyhow.
 * If you don't need this {@link Collection} to re-fresh
 * its content on every call, by doing round-trips to
 * the encapsulated iterable, use {@link Sticky}.</p>
 *
 * <p>There is no thread-safety guarantee.
 * @param <T> List type
 * @see Sticky
 * @since 0.1
 * @deprecated This test class is to be removed after Cactoos-Matchers project
 * remooves the usage of it.
 * @todo #1116:30min Remove this deprecated class after the issue
 *  https://github.com/llorllale/cactoos-matchers/issues/148 has been resolved.
 *  The Cactoos-Matchers project(https://github.com/llorllale/cactoos-matchers)
 *  utilizes this class and by removing the class right now causes issues with
 *  class not found in the Cactoos-Matchers project.
 */
@Deprecated
public final class CollectionOf<T> implements Collection<T> {
    /**
     * Collection.
     */
    private final Unchecked<Collection<T>> col;

    /**
     * Ctor.
     *
     * @param array An array of some elements
     */
    @SafeVarargs
    public CollectionOf(final T... array) {
        this(new IterableOf<>(array));
    }

    /**
     * Ctor.
     * @param src An {@link Iterable}
     */
    public CollectionOf(final Iterable<T> src) {
        this(
            () -> {
                final Collection<T> list = new LinkedList<>();
                src.forEach(list::add);
                return list;
            }
        );
    }

    /**
     * Ctor.
     * @param slr The scalar
     */
    public CollectionOf(final Scalar<Collection<T>> slr) {
        this.col = new Unchecked<>(slr);
    }

    @Override
    public int size() {
        return this.col.value().size();
    }

    @Override
    public boolean isEmpty() {
        return this.col.value().isEmpty();
    }

    @Override
    public boolean contains(final Object obj) {
        return this.col.value().contains(obj);
    }

    @Override
    public Iterator<T> iterator() {
        return this.col.value().iterator();
    }

    @Override
    public Object[] toArray() {
        return this.col.value().toArray();
    }

    @Override
    public <X> X[] toArray(final X[] array) {
        return this.col.value().toArray(array);
    }

    @Override
    public boolean add(final T elem) {
        return this.col.value().add(elem);
    }

    @Override
    public boolean remove(final Object obj) {
        return this.col.value().remove(obj);
    }

    @Override
    public boolean containsAll(final Collection<?> other) {
        return this.col.value().containsAll(other);
    }

    @Override
    public boolean addAll(final Collection<? extends T> other) {
        return this.col.value().addAll(other);
    }

    @Override
    public boolean removeAll(final Collection<?> other) {
        return this.col.value().removeAll(other);
    }

    @Override
    public boolean retainAll(final Collection<?> other) {
        return this.col.value().retainAll(other);
    }

    @Override
    public void clear() {
        this.col.value().clear();
    }

    @Override
    public String toString() {
        return this.col.value().toString();
    }

    @Override
    @SuppressFBWarnings("EQ_UNUSUAL")
    public boolean equals(final Object other) {
        return new Unchecked<>(
            new Or(
                () -> other == this,
                new And(
                    () -> other != null,
                    () -> Collection.class.isAssignableFrom(other.getClass()),
                    () -> {
                        final Collection<?> compared = (Collection<?>) other;
                        return this.size() == compared.size();
                    },
                    () -> {
                        final Collection<?> compared = (Collection<?>) other;
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

    @Override
    public int hashCode() {
        return new HashCode(this).value();
    }
}

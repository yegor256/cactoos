/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.util.Collection;
import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.iterator.Immutable;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.InheritanceLevel;
import org.cactoos.scalar.SumOfIntScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Base collection.
 *
 * <p>There is no thread-safety guarantee.</p>
 * @param <X> Element type
 * @since 0.23
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming"
    }
)
public abstract class CollectionEnvelope<X> implements Collection<X> {

    /**
     * Shuffled one.
     */
    private final UncheckedScalar<Collection<X>> col;

    /**
     * Ctor.
     * @param slr The scalar
     */
    public CollectionEnvelope(final Scalar<Collection<X>> slr) {
        this.col = new UncheckedScalar<>(slr);
    }

    @Override
    public final int size() {
        return this.col.value().size();
    }

    @Override
    public final boolean isEmpty() {
        return this.col.value().isEmpty();
    }

    @Override
    public final Iterator<X> iterator() {
        return new Immutable<>(this.col.value().iterator());
    }

    @Override
    public final boolean contains(final Object object) {
        return this.col.value().contains(object);
    }

    @Override
    public final Object[] toArray() {
        return this.col.value().toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public final <T> T[] toArray(final T[] array) {
        return this.col.value().toArray(array);
    }

    @Override
    public final boolean add(final X item) {
        throw new UnsupportedOperationException(
            "#add(): the collection is ready only"
        );
    }

    @Override
    public final boolean remove(final Object object) {
        throw new UnsupportedOperationException(
            "#remove(): the collection is ready only"
        );
    }

    @Override
    public final boolean containsAll(final Collection<?> list) {
        return this.col.value().containsAll(list);
    }

    @Override
    public final boolean addAll(final Collection<? extends X> list) {
        throw new UnsupportedOperationException(
            "#addAll(): the collection is ready only"
        );
    }

    @Override
    public final boolean removeAll(final Collection<?> list) {
        throw new UnsupportedOperationException(
            "#removeAll(): the collection is ready only"
        );
    }

    @Override
    public final boolean retainAll(final Collection<?> list) {
        throw new UnsupportedOperationException(
            "#retainAll(): the collection is ready only"
        );
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the collection is ready only"
        );
    }

    // @checkstyle DesignForExtensionCheck (5 lines)
    @Override
    public String toString() {
        return this.col.value().toString();
    }

    @Override
    public final boolean equals(final Object other) {
        return new UncheckedScalar<>(
            new And(
                () -> other != null,
                () -> new InheritanceLevel(
                    other.getClass(), CollectionEnvelope.class
                ).value() > -1,
                () -> {
                    final Collection<?> compared = (Collection<?>) other;
                    return this.size() == compared.size();
                },
                () -> {
                    final Iterable<?> compared = (Iterable<?>) other;
                    final Iterator<?> iterator = compared.iterator();
                    return new UncheckedScalar<>(
                        new And(
                            (X input) -> input.equals(iterator.next()),
                            this
                        )
                    ).value();
                }
            )
        ).value();
    }

    // @checkstyle MagicNumberCheck (30 lines)
    @Override
    public final int hashCode() {
        return new UncheckedScalar<>(
            new Folded<>(
                42,
                (hash, entry) -> new SumOfIntScalar(
                    () -> 37 * hash,
                    entry::hashCode
                ).value(),
                this
            )
        ).value();
    }
}

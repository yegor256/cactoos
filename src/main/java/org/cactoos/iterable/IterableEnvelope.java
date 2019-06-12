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
package org.cactoos.iterable;

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.iterator.Immutable;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.SumOfInt;
import org.cactoos.scalar.Unchecked;

/**
 * Iterable envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.24
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class IterableEnvelope<X> implements Iterable<X> {

    /**
     * The iterable.
     */
    private final Unchecked<Iterable<X>> iterable;

    /**
     * Ctor.
     * @param scalar The source
     */
    public IterableEnvelope(final Scalar<Iterable<X>> scalar) {
        this.iterable = new Unchecked<>(scalar);
    }

    @Override
    public final Iterator<X> iterator() {
        return new Immutable<>(
            this.iterable.value().iterator()
        );
    }

    @Override
    public final boolean equals(final Object other) {
        return new Unchecked<>(
            new Or(
                () -> other == this,
                new And(
                    () -> other != null,
                    () -> Iterable.class.isAssignableFrom(other.getClass()),
                    () -> {
                        final Iterable<?> compared = (Iterable<?>) other;
                        final Iterator<?> iterator = compared.iterator();
                        return new Unchecked<>(
                            new And(
                                (X input) -> input.equals(iterator.next()),
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
    public final int hashCode() {
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
}

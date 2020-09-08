/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import java.util.NoSuchElementException;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Paged iterable.
 * Elements will continue to be provided so long as {@code next} produces
 * non-empty iterators.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.47
 */
@SuppressWarnings("PMD.OnlyOneConstructorShouldDoInitialization")
public final class Paged<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * <p>
     * @param first First bag of elements
     * @param next Subsequent bags of elements
     * @param <I> Custom iterator
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public <I extends Iterator<X>> Paged(
        final Scalar<I> first, final Func<I, I> next
    ) {
        // @checkstyle AnonInnerLengthCheck (30 lines)
        super(
            new IterableOf<>(
                () -> new Iterator<X>() {
                    private Unchecked<I> current = new Unchecked<>(
                        new Sticky<>(first)
                    );

                    private final UncheckedFunc<I, I> subsequent =
                        new UncheckedFunc<>(next);

                    @Override
                    public boolean hasNext() {
                        if (!this.current.value().hasNext()) {
                            final I next = this.subsequent.apply(
                                this.current.value()
                            );
                            this.current = new Unchecked<>(
                                new Sticky<>(() -> next)
                            );
                        }
                        return this.current.value().hasNext();
                    }

                    @Override
                    public X next() {
                        if (this.hasNext()) {
                            return this.current.value().next();
                        }
                        throw new NoSuchElementException();
                    }
                }
            )
        );
    }
}


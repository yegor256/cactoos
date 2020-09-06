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
import java.util.List;
import java.util.NoSuchElementException;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.FallbackFrom;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.iterator.IteratorOf;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.ScalarWithFallback;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.SumOfInt;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * Array as iterable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 0.12
 * @checkstyle ClassDataAbstractionCouplingCheck (550 lines)
 */
@SuppressWarnings("PMD.OnlyOneConstructorShouldDoInitialization")
public final class IterableOf<X> implements Iterable<X> {

    /**
     * The encapsulated iterator.
     */
    private final Scalar<Iterator<X>> itr;

    /**
     * Ctor.
     * @param items The array
     */
    @SafeVarargs
    public IterableOf(final X... items) {
        this(() -> new IteratorOf<>(items));
    }

    /**
     * Ctor.
     * @param list The list
     */
    public IterableOf(final List<X> list) {
        this(list::iterator);
    }

    /**
     * Ctor.
     * @param list The list
     * @since 0.21
     */
    public IterableOf(final Iterator<X> list) {
        this(() -> list);
    }

    /**
     * Paged iterable.
     * <p>
     * Elements will continue to be provided so long as {@code next} produces
     * non-empty iterators.
     * @param first First bag of elements
     * @param next Subsequent bags of elements
     * @param <I> Custom iterator
     * @todo #947:30min Move this constructor in its own class with its own
     *  tests and meaningful name (maybe Paged?). Then remove the
     *  ClassDataAbstractionCouplingCheck suppression for IterableOf.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public <I extends Iterator<X>> IterableOf(
        final Scalar<I> first, final Func<I, I> next
    ) {
        // @checkstyle AnonInnerLengthCheck (30 lines)
        this(
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
        );
    }

    /**
     * Ctor.
     * @param sclr The encapsulated iterator of x
     */
    public IterableOf(final Scalar<Iterator<X>> sclr) {
        this.itr = sclr;
    }

    @Override
    public Iterator<X> iterator() {
        return new Unchecked<>(this.itr).value();
    }

    @Override
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EQ_UNUSUAL")
    @SuppressWarnings (value = "unchecked")
    public boolean equals(final Object other) {
        return new Unchecked<>(
            new Or(
                () -> other == this,
                new And(
                    () -> other != null,
                    () -> Iterable.class.isAssignableFrom(other.getClass()),
                    () -> {
                        final Iterable<X> compared = (Iterable<X>) other;
                        return new ScalarWithFallback<>(
                            new And(
                                (X value) -> true,
                                new Matched<>(
                                    this,
                                    compared
                                )
                            ),
                            new IterableOf<>(
                                new FallbackFrom<>(
                                    IllegalStateException.class,
                                    ex -> false
                                )
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

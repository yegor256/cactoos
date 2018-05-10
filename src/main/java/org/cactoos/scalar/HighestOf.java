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
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Find the highest item.
 *
 * <p>Here is how you can use it to
 * find highest of {@link Comparable} items:</p>
 *
 * <pre>
 * final String highest = new HighestOf&lt;String&gt;(
 *         () -&gt; "Banana", () -&gt; "Apple", () -&gt; "Orange"
 *     ).value();
 * // -&gt; highest == "Orange"
 *
 * final Character highestChar = new HighestOf&lt;&gt;('B', 'U', 'G').value();
 * // -&gt; highestChar == 'U'
 * </pre>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Scalar type
 * @see UncheckedScalar
 * @see IoCheckedScalar
 * @since 0.29
 */
public final class HighestOf<T extends Comparable<T>> implements Scalar<T> {

    /**
     * Result.
     */
    private final Scalar<T> result;

    /**
     * Ctor.
     * @param items The comparable items
     */
    @SafeVarargs
    public HighestOf(final T... items) {
        this(
            new Mapped<>(
                item -> () -> item,
                items
            )
        );
    }

    /**
     * Ctor.
     * @param scalars The scalars
     */
    @SafeVarargs
    public HighestOf(final Scalar<T>... scalars) {
        this(new IterableOf<>(scalars));
    }

    /**
     * Ctor.
     * @param iterable The items
     */
    public HighestOf(final Iterable<Scalar<T>> iterable) {
        this.result = new Reduced<>(
            (first, second) -> {
                final T value;
                if (first.compareTo(second) > 0) {
                    value = first;
                } else {
                    value = second;
                }
                return value;
            },
            iterable
        );
    }

    @Override
    public T value() throws Exception {
        return this.result.value();
    }
}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
package org.cactoos.number;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Reduced;

/**
 * Find the greater among items.
 *
 * <p>
 * Here is how you can use it to find the max of a set of numbers:
 * </p>
 *
 * <pre>
 * int max = new MaxOf(1, 2, 3, 4).intValue();
 * long max = new MaxOf(1L, 2L, 3L).longValue();
 * int max = new MaxOf(numbers).intValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class MaxOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 8337955195592696602L;

    /**
     * Ctor.
     * @param src Numbers
     */
    public MaxOf(final Number... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public MaxOf(final Iterable<? extends Number> src) {
        super(
            new NumberOfScalars(
                new Reduced<>(
                    Math::max,
                    new Mapped<>((Number n) -> n::longValue, src)
                ),
                new Reduced<>(
                    Math::max,
                    new Mapped<>((Number n) -> n::intValue, src)
                ),
                new Reduced<>(
                    Math::max,
                    new Mapped<>((Number n) -> n::floatValue, src)
                ),
                new Reduced<>(
                    Math::max,
                    new Mapped<>((Number n) -> n::doubleValue, src)
                )
            )
        );
    }
}

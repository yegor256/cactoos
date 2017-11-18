/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.cactoos.Scalar;
import org.cactoos.func.MinFunc;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Find the smaller among items.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * <p>This class implements {@link OneOf}.</p>
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @author Eduard Balovnev (bedward70@mail.ru)
 * @version $Id$
 * @param <T> Scalar type
 * @since 0.9
 */
public class MinOf<T extends Comparable<T>> extends OneOf<T> {

    /**
     * Ctor.
     * @param scalars The items
     */
    @SafeVarargs
    public MinOf(final Scalar<T>... scalars) {
        this(new IterableOf<>(scalars));
    }

    /**
     * Ctor.
     * @param iterable The items
     */
    public MinOf(final Iterable<Scalar<T>> iterable) {
        super(new MinFunc<>(), iterable);
    }

    /**
     * Find the smaller among integers.
     *
     * <p>There is no thread-safety guarantee.</p>
     *
     * <p>This class implements {@link MinOf}.</p>
     *
     * @author Eduard Balovnev (bedward70@mail.ru)
     * @version $Id$
     * @since 1.0
     */
    public static class Integers extends MinOf<Integer> {

        /**
         * Ctor.
         * @param src Array of integers
         */
        public Integers(final int... src) {
            super(
                new Mapped<>(
                    input -> () -> input,
                    IntStream.of(src).boxed().collect(Collectors.toList())
                )
            );
        }
    }

    /**
     * Find the smaller among longs.
     *
     * <p>There is no thread-safety guarantee.</p>
     *
     * <p>This class implements {@link MinOf}.</p>
     *
     * @author Eduard Balovnev (bedward70@mail.ru)
     * @version $Id$
     * @since 1.0
     */
    public static class Longs extends MinOf<Long> {

        /**
         * Ctor.
         * @param src Array of longs
         */
        public Longs(final long... src) {
            super(
                new Mapped<>(
                    input -> () -> input,
                    LongStream.of(src).boxed().collect(Collectors.toList())
                )
            );
        }
    }

    /**
     * Find the smaller among doubles.
     *
     * <p>There is no thread-safety guarantee.</p>
     *
     * <p>This class implements {@link MinOf}.</p>
     *
     * @author Eduard Balovnev (bedward70@mail.ru)
     * @version $Id$
     * @since 1.0
     */
    public static class Doubles extends MinOf<Double> {

        /**
         * Ctor.
         * @param src Array of doubles
         */
        public Doubles(final double... src) {
            super(
                new Mapped<>(
                    input -> () -> input,
                    DoubleStream.of(src).boxed().collect(Collectors.toList())
                )
            );
        }
    }
}

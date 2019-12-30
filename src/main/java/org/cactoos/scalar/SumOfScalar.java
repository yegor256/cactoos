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
package org.cactoos.scalar;

import java.util.stream.Collectors;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;

/**
 * Make a scalar which is sum of scalar's values.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. Despite that this class does NOT throw a checked
 * exception.</p>
 *
 * <p>There is no thread-safety guarantee.
 * <p>Note this class is for internal usage only
 *
 * @since 0.30
 * @todo #1266:30min This class uses Java8 Stream API. Refactor the
 *  {@link value} method with the usage of {@link org.cactoos.iterable.Mapped}
 *  iterable as an elegant way. Also remove over-complicated construction of
 *  iterable and list.
 */
final class SumOfScalar implements Scalar<SumOf> {

    /**
     * Varargs of Scalar to sum up values from.
     */
    private final Scalar<? extends Number>[] scalars;

    /**
     * Ctor.
     * @param src Varargs of Scalar to sum up values from
     * @since 0.30
     */
    @SafeVarargs
    SumOfScalar(final Scalar<? extends Number>... src) {
        this.scalars = src;
    }

    @Override
    public SumOf value() {
        return new SumOf(
            new IterableOf<>(
                new ListOf<>(this.scalars)
                    .stream()
                    .map(
                        scalar -> new Unchecked<>(scalar).value()
                    ).collect(Collectors.toList())
            )
        );
    }
}

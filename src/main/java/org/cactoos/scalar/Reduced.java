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

import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.iterable.Mapped;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Reduces iterable via BiFunc.
 *
 * <pre>{@code
 * new Reduced<>(
 *     (first, last) -> first + last,
 *     new IterableOf<>(() -> 1L, () -> 2L, () -> 3L, () -> 4L)
 * ).value() // returns 10L
 * }</pre>
 *
 * <p>Here is how you can use it to
 * find one of items according to the specified {@link BiFunc}:</p>
 *
 * <pre>{@code
 * final String apple = new Reduced<>(
 *     (first, last) -> first,
 *     new IterableOf<Scalar<String>>(
 *         () -> "Apple",
 *         () -> "Banana",
 *         () -> "Orange"
 *     )
 * ).value();
 * final String orange = new Reduced<>(
 *     (first, last) -> last,
 *     new IterableOf<Scalar<String>>(
 *         () -> "Apple",
 *         () -> "Banana",
 *         () -> "Orange"
 *     )
 * ).value();
 * }</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * @param <T> Scalar type
 * @since 0.30
 */
public final class Reduced<T> implements Scalar<T> {

    /**
     * Items.
     */
    private final Iterable<Scalar<T>> items;

    /**
     * Folding function.
     */
    private final BiFunc<T, T, T> function;

    /**
     * Ctor.
     * @param reduce Reducing function
     * @param scalars The scalars
     */
    public Reduced(
        final BiFunc<T, T, T> reduce,
        final Iterable<Scalar<T>> scalars
    ) {
        this.items = scalars;
        this.function = reduce;
    }

    /**
     * Ctor.
     * @param reduce Reducing function
     * @param values Values to be wrapped as scalars
     */
    @SafeVarargs
    public Reduced(
        final BiFunc<T, T, T> reduce,
        final T... values
    ) {
        this(reduce, new Mapped<>(Constant::new, values));
    }

    @Override
    public T value() throws Exception {
        final Iterator<Scalar<T>> iter = this.items.iterator();
        if (!iter.hasNext()) {
            throw new NoSuchElementException(
                "Can't find first element in an empty iterable"
            );
        }
        T acc = iter.next().value();
        while (iter.hasNext()) {
            final T next = iter.next().value();
            acc = this.function.apply(acc, next);
        }
        return acc;
    }
}

/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.iterable.Mapped;

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
    private final Iterable<? extends Scalar<? extends T>> items;

    /**
     * Folding function.
     */
    private final BiFunc<? super T, ? super T, ? extends T> function;

    /**
     * Ctor.
     * @param reduce Reducing function
     * @param values Values to be wrapped as scalars
     */
    @SafeVarargs
    public Reduced(
        final BiFunc<? super T, ? super T, ? extends T> reduce,
        final T... values
    ) {
        this(reduce, new Mapped<>(Constant::new, values));
    }

    /**
     * Ctor.
     * @param values Values to be wrapped as scalars
     * @param reduce Reducing function
     * @since 0.55.0
     */
    public Reduced(
        final Iterable<T> values,
        final BiFunc<? super T, ? super T, ? extends T> reduce
    ) {
        this(reduce, new Mapped<>(Constant::new, values));
    }

    /**
     * Ctor.
     * @param reduce Reducing function
     * @param scalars The scalars
     */
    public Reduced(
        final BiFunc<? super T, ? super T, ? extends T> reduce,
        final Iterable<? extends Scalar<? extends T>> scalars
    ) {
        this.items = scalars;
        this.function = reduce;
    }

    @Override
    public T value() throws Exception {
        final Iterator<? extends Scalar<? extends T>> iter = this.items.iterator();
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

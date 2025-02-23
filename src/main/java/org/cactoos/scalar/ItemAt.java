/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.FormattedText;

/**
 * Element from position in {@link Iterable}
 * or fallback value if iterable doesn't have this position.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Scalar type
 * @since 0.7
 */
public final class ItemAt<T> implements Scalar<T> {

    /**
     * {@link Sticky} that holds the value of the {@link Scalar}
     *  at the position specified in the constructor.
     */
    private final Scalar<T> saved;

    /**
     * Ctor.
     *
     * @param position Position
     * @param iterable Iterable
     */
    public ItemAt(final int position, final Iterable<? extends T> iterable) {
        this(
            position,
            itr -> {
                throw new IOException(
                    new FormattedText(
                        "The iterable doesn't have the position #%d",
                        position
                    ).asString()
                );
            },
            iterable
        );
    }

    /**
     * Ctor.
     *
     * @param position Position
     * @param fallback Fallback value
     * @param iterable Iterable
     */
    public ItemAt(
        final int position,
        final T fallback,
        final Iterable<? extends T> iterable
    ) {
        this(position, new FuncOf<>(new Constant<>(fallback)), iterable);
    }

    /**
     * Ctor.
     *
     * @param position Position
     * @param fallback Fallback value
     * @param iterable Iterable
     */
    public ItemAt(
        final int position,
        final Func<? super Iterable<? extends T>, ? extends T> fallback,
        final Iterable<? extends T> iterable
    ) {
        this.saved = new Sticky<>(
            () -> {
                if (position < 0) {
                    throw new IOException(
                        new FormattedText(
                            "The position must be non-negative: %d",
                            position
                        ).asString()
                    );
                }
                final Iterator<? extends T> src = iterable.iterator();
                int cur;
                for (cur = 0; cur < position && src.hasNext(); ++cur) {
                    src.next();
                }
                final T ret;
                if (cur == position && src.hasNext()) {
                    ret = src.next();
                } else {
                    ret = fallback.apply(new IterableOf<>(src));
                }
                return ret;
            }
        );
    }

    @Override
    public T value() throws Exception {
        return this.saved.value();
    }

}

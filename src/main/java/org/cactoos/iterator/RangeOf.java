/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;

/**
 * Iterator implementation to model range functionality.
 * @param <T> Range value type
 * @since 0.50
 */
public final class
    RangeOf<T extends Comparable<T>> implements Iterator<T> {

    /**
     * Incrementor.
     */
    private final UncheckedFunc<? super T, ? extends T> inc;

    /**
     * Current value.
     */
    private final AtomicReference<T> value;

    /**
     * Maximum value.
     */
    private final T max;

    /**
     * Ctor.
     * @param min Start of the range.
     * @param max End of the range.
     * @param incrementor The {@link Func} to process for the next value.
     */
    public RangeOf(final T min, final T max,
        final Func<? super T, ? extends T> incrementor) {
        this.inc = new UncheckedFunc<>(incrementor);
        this.value = new AtomicReference<>(min);
        this.max = max;
    }

    @Override
    public boolean hasNext() {
        return this.value.get().compareTo(this.max) < 1;
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        final T result = this.value.get();
        this.value.set(this.inc.apply(result));
        return result;
    }
}

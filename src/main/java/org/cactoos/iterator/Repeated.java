/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Repeat an element.
 *
 * <p>If you need to repeat endlessly, use {@link Endless}.</p>
 *
 * @param <T> Element type
 * @since 0.4
 */
public final class Repeated<T> implements Iterator<T> {

    /**
     * The element to repeat.
     */
    private final Unchecked<? extends T> elm;

    /**
     * How many more repeats will happen.
     */
    private int repeat;

    /**
     * Ctor.
     * @param max How many times to repeat
     * @param element Element to repeat
     */
    public Repeated(final int max, final T element) {
        this(max, () -> element);
    }

    /**
     * Ctor.
     * @param max How many times to repeat
     * @param scalar Scalar to repeat
     */
    public Repeated(final int max, final Scalar<? extends T> scalar) {
        this.repeat = max;
        this.elm = new Unchecked<>(scalar);
    }

    @Override
    public boolean hasNext() {
        return this.repeat > 0;
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator can't repeat anymore."
            );
        }
        --this.repeat;
        return this.elm.value();
    }
}

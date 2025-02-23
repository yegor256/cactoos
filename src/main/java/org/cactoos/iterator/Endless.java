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
 * Iterator that never ends.
 *
 * <p>If you need to repeat certain amount of time,
 * use {@link Repeated}.</p>
 *
 * @param <T> Element type
 * @since 0.4
 */
public final class Endless<T> implements Iterator<T> {

    /**
     * The element to repeat.
     */
    private final Unchecked<? extends T> origin;

    /**
     * Ctor.
     * @param element Element to repeat
     */
    public Endless(final T element) {
        this(() -> element);
    }

    /**
     * Ctor.
     * @param scalar Scalar to repeat
     */
    public Endless(final Scalar<? extends T> scalar) {
        this(new Unchecked<>(scalar));
    }

    /**
     * Ctor.
     * @param scalar Scalar to repeat
     */
    private Endless(final Unchecked<? extends T> scalar) {
        this.origin = scalar;
    }

    @Override
    public boolean hasNext() {
        return this.origin.value() != null;
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have item"
            );
        }
        return this.origin.value();
    }
}

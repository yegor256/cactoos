/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Shuffled iterator.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.20
 */
public final class Shuffled<T> implements Iterator<T> {

    /**
     * Shuffled scalar.
     */
    private final Unchecked<Iterator<T>> scalar;

    /**
     * Ctor.
     * @param iterator The original iterator
     */
    public Shuffled(final Iterator<? extends T> iterator) {
        this(new SecureRandom(), iterator);
    }

    /**
     * Ctor.
     * @param random Randomizer.
     * @param iterator The original iterator
     */
    public Shuffled(final Random random, final Iterator<? extends T> iterator) {
        this.scalar = new Unchecked<>(
            new Sticky<>(
                () -> {
                    final List<T> items = new ListOf<>(iterator);
                    Collections.shuffle(items, random);
                    return items.iterator();
                }
            )
        );
    }

    @Override
    public boolean hasNext() {
        return this.scalar.value().hasNext();
    }

    @Override
    public T next() {
        return this.scalar.value().next();
    }
}

/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

/**
 * Endless iterable.
 *
 * <p>If you need to repeat certain amount of time,
 * use {@link Repeated}.</p>
 *
 * @param <T> Element type
 * @since 0.4
 */
public final class Endless<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param item The item to repeat
     */
    public Endless(final T item) {
        super(new IterableOf<>(() -> new org.cactoos.iterator.Endless<>(item)));
    }

}

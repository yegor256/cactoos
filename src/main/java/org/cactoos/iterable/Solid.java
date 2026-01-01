/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

/**
 * An {@link Iterable} that is both synchronized and sticky.
 *
 * <p>Objects of this class are thread-safe.</p>
 *
 * @param <X> Type of item
 * @since 0.24
 */
public final class Solid<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * @param src The underlying iterable
     */
    @SafeVarargs
    public Solid(final X... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param iterable The iterable
     */
    public Solid(final Iterable<? extends X> iterable) {
        super(new Synced<>(new Sticky<>(iterable)));
    }

}

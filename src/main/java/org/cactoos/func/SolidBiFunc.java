/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import org.cactoos.BiFunc;

/**
 * BiFunc that is thread-safe and sticky.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @param <X> Type of first input
 * @param <Y> Type of second input
 * @param <Z> Type of output
 * @since 0.24
 */
public final class SolidBiFunc<X, Y, Z> extends BiFuncEnvelope<X, Y, Z> {

    /**
     * Ctor.
     * @param fnc Func original
     */
    public SolidBiFunc(final BiFunc<X, Y, Z> fnc) {
        this(fnc, Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     * @param fnc Func original
     * @param max Max buffer length
     * @since 0.26
     */
    public SolidBiFunc(final BiFunc<X, Y, Z> fnc, final int max) {
        super(new SyncBiFunc<>(new StickyBiFunc<>(fnc, max)));
    }
}

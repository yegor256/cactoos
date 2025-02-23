/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.Callable;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.func.FuncOf;

/**
 * ScalarOf.
 *
 * @param <T> Element type
 * @since 0.4
 */
public final class ScalarOf<T> extends ScalarEnvelope<T> {
    /**
     * Ctor.
     * @param runnable Encapsulated proc
     * @param result Result to return
     * @since 0.48
     */
    public ScalarOf(final Runnable runnable, final T result) {
        this(() -> {
            runnable.run();
            return result;
        });
    }

    /**
     * Ctor.
     * @param proc Encapsulated proc
     * @param ipt Input
     * @param result Result to return
     * @param <X> Type of input
     * @since 0.48
     */
    public <X> ScalarOf(final Proc<? super X> proc, final X ipt, final T result) {
        this(new FuncOf<>(proc, result), ipt);
    }

    /**
     * Ctor.
     * @param fnc Encapsulated func
     * @param ipt Input
     * @param <X> Type of input
     * @since 0.41
     */
    public <X> ScalarOf(final Func<? super X, ? extends T> fnc, final X ipt) {
        this(() -> fnc.apply(ipt));
    }

    /**
     * Ctor.
     *
     * @param origin The scalar
     */
    public ScalarOf(final Callable<? extends T> origin) {
        super(origin::call);
    }
}

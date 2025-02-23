/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.concurrent.Callable;
import org.cactoos.Scalar;

/**
 * Func as {@link Callable}.
 *
 * <p>You may want to use this decorator where
 * {@link Callable} is required, but you just have a function:</p>
 *
 * <pre> Callable&lt;String&gt; callable = new CallableOf&lt;&gt;(
 *   i -&gt; "Hello, world!"
 * );
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of output
 * @since 0.12
 */
public final class CallableOf<T> extends CallableEnvelope<T> {

    /**
     * Ctor.
     * @param slr Encapsulated scalar
     * @since 0.41
     */
    public CallableOf(final Scalar<? extends T> slr) {
        super(slr::value);
    }
}

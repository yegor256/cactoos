/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.StickyFunc;

/**
 * Cached version of a Scalar.
 *
 * <p>This {@link Scalar} decorator technically is an in-memory
 * cache.</p>
 *
 * <p>Pay attention that this class is not thread-safe. It is highly
 * recommended to always decorate it with {@link Synced}.</p>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <pre>{@code
 * final Scalar<Integer> scalar = new StickyScalar<>(
 *     () -> {
 *         System.out.println("Will be printed only once");
 *         return new SecureRandom().nextInt();
 *     }
 * ).value()
 * }</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @see StickyFunc
 * @since 0.3
 */
public final class Sticky<T> implements Scalar<T> {

    /**
     * Func.
     */
    private final Func<Boolean, ? extends T> func;

    /**
     * Ctor.
     * @param scalar The Scalar to cache
     */
    public Sticky(final Scalar<? extends T> scalar) {
        this.func = new StickyFunc<>(
            input -> scalar.value()
        );
    }

    @Override
    public T value() throws Exception {
        return this.func.apply(true);
    }
}

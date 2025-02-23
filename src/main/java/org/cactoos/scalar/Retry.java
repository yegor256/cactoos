/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.time.Duration;
import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * Func that will try a few times before throwing an exception.
 *
 * <pre>{@code
 * new RetryScalar<>(
 *     () -> {
 *         if (new SecureRandom().nextDouble() > 0.3d) {
 *             throw new IllegalArgumentException("May happen");
 *         }
 *         return 0;
 *     },
 *     5
 * ).value() // will try to run 5 times before throwing an exception
 * }</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * @param <T> Type of output
 * @since 0.9
 */
public final class Retry<T> implements Scalar<T> {

    /**
     * Original scalar.
     */
    private final Scalar<? extends T> origin;

    /**
     * Exit condition.
     */
    private final Func<Integer, Boolean> func;

    /**
     * Wait between executions.
     */
    private final Duration wait;

    /**
     * Ctor.
     * @param scalar Scalar original
     */
    public Retry(final Scalar<? extends T> scalar) {
        this(scalar, Duration.ZERO);
    }

    /**
     * Ctor.
     * @param scalar Scalar original
     * @param wait The {@link Duration} to wait between attempts
     */
    public Retry(final Scalar<? extends T> scalar, final Duration wait) {
        this(scalar, 3, wait);
    }

    /**
     * Ctor.
     * @param scalar Scalar original
     * @param attempts Maximum number of attempts
     */
    public Retry(final Scalar<? extends T> scalar, final int attempts) {
        this(scalar, attempts, Duration.ZERO);
    }

    /**
     * Ctor.
     * @param scalar Scalar original
     * @param attempts Maximum number of attempts
     * @param wait The {@link Duration} to wait between attempts
     */
    public Retry(final Scalar<? extends T> scalar, final int attempts,
        final Duration wait) {
        this(scalar, attempt -> attempt >= attempts, wait);
    }

    /**
     * Ctor.
     * @param scalar Func original
     * @param exit Exit condition, returns TRUE if there is no reason to try
     */
    public Retry(final Scalar<? extends T> scalar,
        final Func<Integer, Boolean> exit) {
        this(scalar, exit, Duration.ZERO);
    }

    /**
     * Ctor.
     * @param scalar Func original
     * @param exit Exit condition, returns TRUE if there is no reason to try
     * @param wait The {@link Duration} to wait between attempts
     */
    public Retry(final Scalar<? extends T> scalar,
        final Func<Integer, Boolean> exit, final Duration wait) {
        this.origin = scalar;
        this.func = exit;
        this.wait = wait;
    }

    @Override
    public T value() throws Exception {
        return new org.cactoos.func.Retry<>(
            (Func<Boolean, T>) input -> this.origin.value(),
            this.func,
            this.wait
        ).apply(true);
    }
}

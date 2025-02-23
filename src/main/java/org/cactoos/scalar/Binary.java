/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Binary operation.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <pre>{@code
 * final AtomicInteger counter = new AtomicInteger();
 * new Binary(
 *     () -> true,
 *     () -> counter.incrementAndGet
 * ).value() // will be equal true
 * }</pre>
 *
 * @since 1.0
 */
public final class Binary implements Scalar<Boolean> {

    /**
     * Scalar for condition.
     */
    private final Scalar<Boolean> condition;

    /**
     * Proc executed when condition is true.
     */
    private final Runnable consequent;

    /**
     * Ctor.
     *
     * @param condition Boolean function
     * @param consequent Proc executed when condition is true
     */
    public Binary(
        final Scalar<Boolean> condition,
        final Runnable consequent
    ) {
        this.condition = condition;
        this.consequent = consequent;
    }

    @Override
    public Boolean value() throws Exception {
        final Boolean result = this.condition.value();
        if (result) {
            this.consequent.run();
        }
        return result;
    }
}

/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Scalar.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Scalar}, you can use
 * {@link Unchecked} decorator. Also
 * you may try {@link IoChecked}.</p>
 *
 * <p>If you want to cache the result of the {@link Scalar} and
 * make sure it doesn't calculate anything twice, you can use
 * {@link Sticky} decorator.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @see Sticky
 * @see Unchecked
 * @see IoChecked
 * @since 0.1
 */
@FunctionalInterface
public interface Scalar<T> {

    /**
     * Convert it to the value.
     * @return The value
     * @throws Exception If fails
     */
    T value() throws Exception;

}

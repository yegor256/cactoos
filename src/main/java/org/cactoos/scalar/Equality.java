/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Bytes;
import org.cactoos.Scalar;

/**
 * Equality.
 *
 * Returns:
 *         the value {@code 0} if {@code x == y};
 *         the value {@code -1} if {@code x < y};
 *         the value {@code 1} if {@code x > y}
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of input
 * @since 0.31
 */
public final class Equality<T extends Bytes> implements Scalar<Integer> {

    /**
     * Left.
     */
    private final T left;

    /**
     * Right.
     */
    private final T right;

    /**
     * Ctor.
     * @param lft Left
     * @param rght Right
     */
    public Equality(final T lft, final T rght) {
        this.left = lft;
        this.right = rght;
    }

    @Override
    public Integer value() throws Exception {
        final byte[] lft = this.left.asBytes();
        final byte[] rght = this.right.asBytes();
        return new Ternary<>(
            () -> lft.length == rght.length,
            () -> {
                int result = 0;
                for (int idx = rght.length - 1; idx >= 0; --idx) {
                    result = (int) lft[idx] - (int) rght[idx];
                    if (result != 0) {
                        break;
                    }
                }
                return Integer.signum(result);
            },
            () -> Integer.signum(lft.length - rght.length)
        ).value();
    }
}

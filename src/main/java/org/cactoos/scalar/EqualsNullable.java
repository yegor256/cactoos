/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Checks 2 objects for equality.
 * Null values are accepted.
 * <p>There is no thread-safety guarantee.
 * @since 1.0
 */
public final class EqualsNullable implements Scalar<Boolean> {
    /**
     * The first object for comparison.
     */
    private final Scalar<? extends Object> first;

    /**
     * The second object for comparison.
     */
    private final Scalar<? extends Object> second;

    /**
     * Accepts 2 objects to compare.
     * @param first Object to compare
     * @param second Object to compare with
     */
    public EqualsNullable(final Object first, final Object second) {
        this(() -> first, () -> second);
    }

    /**
     * Accepts scalar to get value from and object to compare with.
     * @param first Scalar to get value to compare
     * @param second Object to compare with
     */
    public EqualsNullable(final Scalar<? extends Object> first, final Object second) {
        this(first, () -> second);
    }

    /**
     * Accepts object to compare with and scalar to get value from.
     * @param first Object to compare
     * @param second Scalar to get value to compare
     */
    public EqualsNullable(final Object first, final Scalar<? extends Object> second) {
        this(() -> first, second);
    }

    /**
     * Accepts 2 scalars to get get values from.
     * @param first Scalar to get value to compare
     * @param second Scalar to get value to compare with
     */
    public EqualsNullable(final Scalar<? extends Object> first,
        final Scalar<? extends Object> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    public Boolean value() throws Exception {
        final Object source = this.first.value();
        final Object compared = this.second.value();
        return source == compared
            || source != null && source.equals(compared);
    }
}

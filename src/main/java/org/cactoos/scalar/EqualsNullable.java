/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
    private final Scalar<Object> first;
    /**
     * The second object for comparison.
     */
    private final Scalar<Object> second;

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
    public EqualsNullable(final Scalar<Object> first, final Object second) {
        this(first, () -> second);
    }

    /**
     * Accepts object to compare with and scalar to get value from.
     * @param first Object to compare
     * @param second Scalar to get value to compare
     */
    public EqualsNullable(final Object first, final Scalar<Object> second) {
        this(() -> first, second);
    }

    /**
     * Accepts 2 scalars to get get values from.
     * @param first Scalar to get value to compare
     * @param second Scalar to get value to compare with
     */
    public EqualsNullable(final Scalar<Object> first,
        final Scalar<Object> second) {
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

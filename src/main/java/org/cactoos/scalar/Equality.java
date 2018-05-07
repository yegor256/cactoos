/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
                    result = lft[idx] - rght[idx];
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

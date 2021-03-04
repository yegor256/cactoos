/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
 * Element with its index.
 *
 * <p>There is no thread-safety guarantee.
 * @param <T> Scalar type
 * @since 0.50
 */
public final class ScalarWithIndex<T> implements Scalar<T> {
    /**
     * Hold the position specified in the constructor.
     */
    private final int pos;

    /**
     * Hold the {@link Scalar} at the position specified in the constructor.
     */
    private final Scalar<T> scalar;

    /**
     * Ctor.
     * @param pos Position
     * @param scalar Scalar
     */
    public ScalarWithIndex(final Integer pos, final Scalar<T> scalar) {
        this.scalar = scalar;
        this.pos = pos;
    }

    @Override
    public T value() throws Exception {
        return this.scalar.value();
    }

    /**
     * Returns the position specified in the constructor.
     * @return The position
     */
    public int position() {
        return this.pos;
    }
}

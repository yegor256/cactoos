/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
package org.cactoos.bytes;

import org.cactoos.Bytes;
import org.cactoos.Fallback;
import org.cactoos.scalar.ScalarWithFallback;
import org.cactoos.scalar.Unchecked;

/**
 * Bytes that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.3
 * @todo #1615:30m Extract fallback logic for Bytes
 *  to a separate class in accordance
 *  to other XXXWithFallback classes.
 *  Leave only basic exception handling in this class.
 */
public final class UncheckedBytes implements Bytes {

    /**
     * Scalar with fallback.
     */
    private final Unchecked<byte[]> scalar;

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     */
    public UncheckedBytes(final Bytes bts) {
        this(bts, new Fallback.None<>());
    }

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     * @param fbk Fallback
     * @since 0.5
     */
    public UncheckedBytes(
        final Bytes bts,
        final Fallback<byte[]> fbk
    ) {
        this.scalar = new Unchecked<byte[]>(
            new ScalarWithFallback<byte[]>(bts::asBytes, fbk)
        );
    }

    @Override
    public byte[] asBytes() {
        return this.scalar.value();
    }

}

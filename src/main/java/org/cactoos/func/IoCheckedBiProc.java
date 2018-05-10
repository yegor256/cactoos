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
package org.cactoos.func;

import java.io.IOException;
import org.cactoos.BiProc;

/**
 * BiProc that doesn't throw checked {@link Exception}, but throws
 * {@link IOException} instead.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @since 0.22
 */
public final class IoCheckedBiProc<X, Y> implements BiProc<X, Y> {

    /**
     * Original proc.
     */
    private final BiProc<X, Y> proc;

    /**
     * Ctor.
     * @param prc Encapsulated func
     */
    public IoCheckedBiProc(final BiProc<X, Y> prc) {
        this.proc = prc;
    }

    @Override
    public void exec(final X first, final Y second) throws IOException {
        new IoCheckedBiFunc<>(
            new BiFuncOf<>(this.proc, null)
        ).apply(first, second);
    }

}

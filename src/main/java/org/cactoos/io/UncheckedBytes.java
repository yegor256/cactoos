/**
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
package org.cactoos.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.cactoos.Bytes;
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;

/**
 * Bytes that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.3
 */
public final class UncheckedBytes implements Bytes {

    /**
     * Original bytes.
     */
    private final Bytes bytes;

    /**
     * Fallback.
     */
    private final Func<IOException, byte[]> fallback;

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     */
    public UncheckedBytes(final Bytes bts) {
        this(
            bts,
            error -> {
                throw new UncheckedIOException(error);
            }
        );
    }

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     * @param fbk Fallback
     * @since 0.5
     */
    public UncheckedBytes(final Bytes bts,
        final Func<IOException, byte[]> fbk) {
        this.bytes = bts;
        this.fallback = fbk;
    }

    @Override
    public byte[] asBytes() {
        byte[] data;
        try {
            data = this.bytes.asBytes();
        } catch (final IOException ex) {
            data = new UncheckedFunc<>(this.fallback).apply(ex);
        }
        return data;
    }

}

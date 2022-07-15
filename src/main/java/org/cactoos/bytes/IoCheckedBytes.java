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

import java.io.IOException;
import org.cactoos.Bytes;
import org.cactoos.Fallback;
import org.cactoos.scalar.IoChecked;

/**
 * Bytes that doesn't throw checked {@link Exception},
 * but only throws {@link IOException}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.52
 */
public final class IoCheckedBytes implements Bytes {

    /**
     * Scalar with fallback.
     */
    private final IoChecked<byte[]> scalar;

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     */
    public IoCheckedBytes(final Bytes bts) {
        this(bts, new Fallback.None<>());
    }

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     * @param fbk Fallback
     * @since 0.5
     */
    @SuppressWarnings({"unchecked", "PMD.AvoidRethrowingException", "PMD.AvoidCatchingThrowable"})
    public IoCheckedBytes(final Bytes bts, final Fallback<byte[]> fbk) {
        this.scalar = new IoChecked<>(
            () -> {
                byte[] ret;
                try {
                    ret = bts.asBytes();
                } catch (final IOException ex) {
                    throw ex;
                // @checkstyle IllegalCatchCheck (1 line)
                } catch (final Throwable ex) {
                    ret = fbk.apply(ex);
                }
                return ret;
            }
        );
    }

    @Override
    public byte[] asBytes() throws IOException {
        return this.scalar.value();
    }

}

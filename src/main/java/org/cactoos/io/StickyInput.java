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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.scalar.IoCheckedScalar;
import org.cactoos.scalar.StickyScalar;

/**
 * Input that reads only once.
 *
 * <p>Pay attention that this class is not thread-safe. It is highly
 * recommended to always decorate it with {@link SyncInput}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.6
 */
public final class StickyInput implements Input {

    /**
     * The cache.
     */
    private final Scalar<byte[]> cache;

    /**
     * Ctor.
     * @param input The input
     */
    public StickyInput(final Input input) {
        this.cache = new StickyScalar<>(
            () -> {
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                new LengthOf(
                    new TeeInput(input, new OutputTo(baos))
                ).value();
                return baos.toByteArray();
            }
        );
    }

    @Override
    public InputStream stream() throws IOException {
        return new ByteArrayInputStream(
            new IoCheckedScalar<>(this.cache).value()
        );
    }

}

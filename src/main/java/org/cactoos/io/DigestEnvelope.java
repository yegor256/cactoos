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
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.cactoos.Bytes;
import org.cactoos.Input;

/**
 * Digest Envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle AbstractClassNameCheck (500 lines)
 * @checkstyle DesignForExtensionCheck (500 lines)
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class DigestEnvelope implements Bytes {

    /**
     * The input.
     */
    private final Input source;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * The algorithm.
     */
    private final String algorithm;

    /**
     * Ctor.
     * @param input The input
     * @param algrthm The algorithm
     */
    public DigestEnvelope(final Input input, final String algrthm) {
        // @checkstyle MagicNumber (1 line)
        this(input, 16 << 10, algrthm);
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Buffer size
     * @param algrthm The algorithm
     */
    public DigestEnvelope(
        final Input input,
        final int max,
        final String algrthm
    ) {
        this.source = input;
        this.size = max;
        this.algorithm = algrthm;
    }

    @Override
    public byte[] asBytes() throws IOException {
        try {
            final MessageDigest msg = MessageDigest.getInstance(this.algorithm);
            try (final InputStream stream = this.source.stream()) {
                final byte[] buf = new byte[this.size];
                while (true) {
                    final int len = stream.read(buf);
                    if (len < 0) {
                        break;
                    }
                    msg.update(buf, 0, len);
                }
                return msg.digest();
            }
        } catch (final NoSuchAlgorithmException ex) {
            throw new IOException(ex);
        }
    }

}

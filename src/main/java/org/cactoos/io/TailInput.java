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
import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Input;
import org.cactoos.scalar.MinOf;
import org.cactoos.text.FormattedText;

/**
 * Input showing only last N bytes of the stream.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Krzysztof Krason (Krzysztof.Krason@gmail.com)
 * @version $Id$
 * @since 0.30
 */
public final class TailInput implements Input {

    /**
     * Input to decorate.
     */
    private final Input input;

    /**
     * Number of last bytes to show from the input.
     */
    private final int count;

    /**
     * Maximum number of bytes to read at once.
     */
    private final int max;

    /**
     * Constructor.
     * @param inpt Input to decorate
     * @param bytes Number of last bytes to show from input
     */
    public TailInput(final Input inpt, final int bytes) {
        // @checkstyle MagicNumber (1 line)
        this(inpt, bytes, 16384);
    }

    /**
     * Constructor.
     * @param inpt Input to decorate
     * @param bytes Number of last bytes to show from input
     * @param maximum Maximum number of bytes to read at once
     */
    public TailInput(final Input inpt, final int bytes, final int maximum) {
        this.input = inpt;
        this.count = bytes;
        this.max = maximum;
    }

    @Override
    public InputStream stream() throws IOException {
        if (this.max < this.count) {
            throw new IllegalArgumentException(
                new FormattedText(
                    "Can't tail %d bytes if buffer is set to %d",
                    this.count, this.max
                ).asString()
            );
        }
        final byte[] buffer = new byte[this.max];
        final byte[] response = new byte[this.count];
        int num = 0;
        final InputStream strm = this.input.stream();
        for (int read = strm.read(buffer); read > 0; read = strm.read(buffer)) {
            if (read < this.max && read < this.count) {
                num = this.copyPartial(buffer, response, num, read);
            } else {
                num = this.copy(buffer, response, read);
            }
        }
        return new ByteArrayInputStream(response, 0, num);
    }

    /**
     * Copy full buffer to response.
     * @param buffer The buffer array
     * @param response The response array
     * @param read Number of bytes read in buffer
     * @return Number of bytes in the response buffer
     */
    private int copy(final byte[] buffer, final byte[] response,
        final int read) {
        System.arraycopy(
            buffer, read - this.count, response, 0, this.count
        );
        return new MinOf(this.count, read).intValue();
    }

    /**
     * Copy buffer to response for read count smaller then buffer size.
     * @param buffer The buffer array
     * @param response The response array
     * @param num Number of bytes in response array from previous read
     * @param read Number of bytes read in the buffer
     * @return New count of bytes in the response array
     * @checkstyle ParameterNumberCheck (3 lines)
     */
    private int copyPartial(final byte[] buffer, final byte[] response,
        final int num, final int read) {
        final int result;
        if (num > 0) {
            System.arraycopy(
                response, read, response, 0, this.count - read
            );
            System.arraycopy(buffer, 0, response, this.count - read, read);
            result = this.count;
        } else {
            System.arraycopy(buffer, 0, response, 0, read);
            result = read;
        }
        return result;
    }
}

/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;
import org.cactoos.text.FormattedText;

/**
 * Logged output stream.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.11
 */
@SuppressWarnings("PMD.LoggerIsNotStaticFinal")
public final class LoggingOutputStream extends OutputStream {

    /**
     * The output stream.
     */
    private final OutputStream origin;

    /**
     * Where the data comes goes.
     */
    private final String destination;

    /**
     * The logger.
     */
    private final Logger logger;

    /**
     * Ctor.
     * @param output Destination of data
     * @param dst The name of source data
     */
    public LoggingOutputStream(final OutputStream output, final String dst) {
        this(output, dst, Logger.getLogger(dst));
    }

    /**
     * Ctor.
     * @param output Destination of data
     * @param dst The name of source data
     * @param lgr Message logger
     */
    public LoggingOutputStream(final OutputStream output, final String dst,
        final Logger lgr) {
        super();
        this.origin = output;
        this.destination = dst;
        this.logger = lgr;
    }

    @Override
    public void write(final int data) throws IOException {
        final byte[] buf = {(byte) data};
        this.write(buf, 0, 1);
    }

    @Override
    public void write(final byte[] buf) throws IOException {
        this.write(buf, 0, buf.length);
    }

    @Override
    public void write(final byte[] buf, final int offset,
        final int len) throws IOException {
        final Instant start = Instant.now();
        this.origin.write(buf, offset, len);
        final Instant end = Instant.now();
        this.logger.info(
            new FormattedText(
                "Written %d byte(s) to %s in %dms.",
                len,
                this.destination,
                Duration.between(start, end).toMillis()
            ).asString()
        );
    }

    @Override
    public void close() throws IOException {
        this.origin.close();
    }

    @Override
    public void flush() throws IOException {
        this.origin.flush();
    }

}

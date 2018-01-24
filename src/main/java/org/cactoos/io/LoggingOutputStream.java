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
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logged output stream.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.29
 */
@SuppressWarnings(
    {
        "PMD.AvoidDuplicateLiterals",
        "PMD.LoggerIsNotStaticFinal"
    }
)
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
     * The bytes read.
     */
    private final AtomicLong bytes;

    /**
     * The time took to read.
     */
    private final AtomicLong time;

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
    public LoggingOutputStream(
        final OutputStream output,
        final String dst,
        final Logger lgr
    ) {
        super();
        this.origin = output;
        this.destination = dst;
        this.logger = lgr;
        this.bytes = new AtomicLong();
        this.time = new AtomicLong();
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
        final long millis = Duration.between(start, end).toMillis();
        this.bytes.getAndAdd(len);
        this.time.getAndAdd(millis);
        final Level level = this.logger.getLevel();
        if (!level.equals(Level.INFO)) {
            this.logger.log(
                level,
                String.format(
                    "Written %d byte(s) to %s in %dms.",
                    this.bytes.get(),
                    this.destination,
                    this.time.get()
                )
            );
        }
    }

    @Override
    public void close() throws IOException {
        this.origin.close();
        final Level level = this.logger.getLevel();
        if (level.equals(Level.INFO)) {
            this.logger.log(
                level,
                String.format(
                    "Written %d byte(s) to %s in %dms.",
                    this.bytes.get(),
                    this.destination,
                    this.time.get()
                )
            );
        }
        this.logger.log(
            level,
            String.format(
                "Closed output stream from %s.",
                this.destination
            )
        );
    }

    @Override
    public void flush() throws IOException {
        this.origin.flush();
        final Level level = this.logger.getLevel();
        if (level.equals(Level.INFO)) {
            this.logger.log(
                level,
                String.format(
                    "Written %d byte(s) to %s in %dms.",
                    this.bytes.get(),
                    this.destination,
                    this.time.get()
                )
            );
        }
        this.logger.log(
            level,
            String.format(
                "Flushed output stream from %s.",
                this.destination
            )
        );
    }

}

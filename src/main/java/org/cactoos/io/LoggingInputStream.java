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
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Logged input stream.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
@SuppressWarnings({"PMD.LoggerIsNotStaticFinal", "PMD.MoreThanOneLogger"})
public final class LoggingInputStream extends InputStream {

    /**
     * The input stream.
     */
    private final InputStream origin;

    /**
     * Where the data comes from.
     */
    private final String source;

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
     * Logger level.
     */
    private final UncheckedScalar<Level> level;

    /**
     * Ctor.
     * @param input Source of data
     * @param src The name of source data
     */
    public LoggingInputStream(final InputStream input, final String src) {
        this(input, src, Logger.getLogger(src));
    }

    /**
     * Ctor.
     * @param input Source of data
     * @param src The name of source data
     * @param lgr The message logger
     */
    public LoggingInputStream(
        final InputStream input,
        final String src,
        final Logger lgr
    ) {
        super();
        this.origin = input;
        this.source = src;
        this.logger = lgr;
        this.level = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    Level lvl = lgr.getLevel();
                    if (lvl == null) {
                        Logger parent = lgr;
                        while (lvl == null) {
                            parent = parent.getParent();
                            lvl = parent.getLevel();
                        }
                    }
                    return lvl;
                }
            )
        );
        this.bytes = new AtomicLong();
        this.time = new AtomicLong();
    }

    @Override
    public int read() throws IOException {
        final byte[] buf = new byte[1];
        final int size;
        if (this.read(buf) == -1) {
            size = -1;
        } else {
            size = Byte.toUnsignedInt(buf[0]);
        }
        return size;
    }

    @Override
    public int read(final byte[] buf) throws IOException {
        return this.read(buf, 0, buf.length);
    }

    @Override
    public int read(final byte[] buf, final int offset, final int len)
        throws IOException {
        final Instant start = Instant.now();
        final int byts = this.origin.read(buf, offset, len);
        final Instant end = Instant.now();
        final long millis = Duration.between(start, end).toMillis();
        if (byts > 0) {
            this.bytes.getAndAdd(byts);
            this.time.getAndAdd(millis);
        }
        final UncheckedText msg = new UncheckedText(
            new FormattedText(
                "Read %d byte(s) from %s in %dms.",
                this.bytes.get(),
                this.source,
                this.time.get()
            )
        );
        if (byts > 0) {
            if (!this.level.value().equals(Level.INFO)) {
                this.logger.log(this.level.value(), msg.asString());
            }
        } else {
            if (this.level.value().equals(Level.INFO)) {
                this.logger.info(msg.asString());
            }
        }
        return byts;
    }

    @Override
    public long skip(final long num) throws IOException {
        final long skipped = this.origin.skip(num);
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "Skipped %d byte(s) from %s.",
                    skipped,
                    this.source
                )
            ).asString()
        );
        return skipped;
    }

    @Override
    public int available() throws IOException {
        final int avail = this.origin.available();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "There is(are) %d byte(s) available from %s.",
                    avail,
                    this.source
                )
            ).asString()
        );
        return avail;
    }

    @Override
    public void close() throws IOException {
        this.origin.close();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "Closed input stream from %s.",
                    this.source
                )
            ).asString()
        );
    }

    @Override
    public void mark(final int limit) {
        this.origin.mark(limit);
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "Marked position %d from %s.",
                    limit,
                    this.source
                )
            ).asString()
        );
    }

    @Override
    public void reset() throws IOException {
        this.origin.reset();
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    "Reset input stream from %s.",
                    this.source
                )
            ).asString()
        );
    }

    @Override
    public boolean markSupported() {
        final boolean supported = this.origin.markSupported();
        final String msg;
        if (supported) {
            msg = "Mark and reset are supported from %s";
        } else {
            msg = "Mark and reset NOT supported from %s";
        }
        this.logger.log(
            this.level.value(),
            new UncheckedText(
                new FormattedText(
                    msg,
                    this.source
                )
            ).asString()
        );
        return supported;
    }
}

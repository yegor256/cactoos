/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
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
    private final Unchecked<Level> level;

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
        this.level = new Unchecked<>(
            new Sticky<>(
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
        if (byts > 0) {
            this.bytes.getAndAdd((long) byts);
            final long millis = Duration.between(start, end).toMillis();
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

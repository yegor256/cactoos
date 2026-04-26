/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import java.util.logging.Logger;
import org.cactoos.Output;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Logged output.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
public final class LoggingOutput implements Output {

    /**
     * The output stream.
     */
    private final Output origin;

    /**
     * Where the data comes goes.
     */
    private final String destination;

    /**
     * The Logger, deferred.
     */
    private final Scalar<Logger> logger;

    /**
     * Ctor.
     * @param output Data output
     * @param dst The name of destination data
     */
    public LoggingOutput(final Output output, final String dst) {
        this(output, dst, () -> Logger.getLogger(dst));
    }

    /**
     * Ctor.
     * @param output Data output
     * @param dst The name of destination data
     * @param lgr Message logger
     */
    public LoggingOutput(
        final Output output,
        final String dst,
        final Logger lgr
    ) {
        this(output, dst, () -> lgr);
    }

    /**
     * Ctor.
     * @param output Data output
     * @param dst The name of destination data
     * @param lgr Message logger, deferred
     */
    private LoggingOutput(
        final Output output,
        final String dst,
        final Scalar<Logger> lgr
    ) {
        this.origin = output;
        this.destination = dst;
        this.logger = lgr;
    }

    @Override
    public OutputStream stream() throws Exception {
        return new LoggingOutputStream(
            this.origin.stream(),
            this.destination,
            new Unchecked<>(this.logger).value()
        );
    }
}

/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import java.util.logging.Logger;
import org.cactoos.Output;

/**
 * Logged output.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
@SuppressWarnings("PMD.LoggerIsNotStaticFinal")
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
     * The Logger.
     */
    private final Logger logger;

    /**
     * Ctor.
     * @param output Data output
     * @param dst The name of destination data
     */
    public LoggingOutput(final Output output, final String dst) {
        this(output, dst, Logger.getLogger(dst));
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
        this.origin = output;
        this.destination = dst;
        this.logger = lgr;
    }

    @Override
    public OutputStream stream() throws Exception {
        return new LoggingOutputStream(
            this.origin.stream(),
            this.destination,
            this.logger
        );
    }

}

/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import java.util.logging.Logger;
import org.cactoos.Input;

/**
 * Logged input.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
@SuppressWarnings("PMD.LoggerIsNotStaticFinal")
public final class LoggingInput implements Input {

    /**
     * The input stream.
     */
    private final Input origin;

    /**
     * Where the data comes from.
     */
    private final String source;

    /**
     * The Logger.
     */
    private final Logger logger;

    /**
     * Ctor.
     * @param input Data input
     * @param src The name of source data
     */
    public LoggingInput(final Input input, final String src) {
        this(input, src, Logger.getLogger(src));
    }

    /**
     * Ctor.
     * @param input Data input
     * @param src The name of source data
     * @param lgr Message logger
     */
    public LoggingInput(
        final Input input,
        final String src,
        final Logger lgr
    ) {
        this.origin = input;
        this.source = src;
        this.logger = lgr;
    }

    @Override
    public InputStream stream() throws Exception {
        return new LoggingInputStream(
            this.origin.stream(),
            this.source,
            this.logger
        );
    }

}

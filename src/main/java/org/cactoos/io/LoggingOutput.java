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

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
import java.util.logging.Logger;
import org.cactoos.Input;

/**
 * Logged input.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
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
    public InputStream stream() throws IOException {
        return new LoggingInputStream(
            this.origin.stream(),
            this.source,
            this.logger
        );
    }

}

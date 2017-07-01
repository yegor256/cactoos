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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Fake logger.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.11
 */
@SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
public final class FakeLogger extends Logger {
    /**
     * Ctor.
     */
    public FakeLogger() {
        this("FakeLogger");
    }

    /**
     * Ctor.
     * @param name Logger name
     */
    public FakeLogger(final String name) {
        this(name, null);
    }

    /**
     * Ctor.
     * @param name Logger name
     * @param resource Resource name
     */
    public FakeLogger(final String name, final String resource) {
        super(name, resource);
        this.addHandler(new FakeHandler());
    }

    @Override
    public String toString() {
        return this.getHandlers()[0].toString();
    }
    /**
     * Fake handler logger.
     *
     * <p>There is no thread-safety guarantee.
     *
     * @author Fabricio Cabral (fabriciofx@gmail.com)
     * @version $Id$
     * @since 0.11
     */
    private static final class FakeHandler extends Handler {
        /**
         * Last recorded log.
         */
        private final List<LogRecord> last;
        /**
         * Ctor.
         */
        FakeHandler() {
            super();
            this.last = new ArrayList<>(0);
        }
        @Override
        public void publish(final LogRecord record) {
            this.last.add(record);
        }
        @Override
        public void close() {
            // Intended empty.
        }
        @Override
        public void flush() {
            // Intended empty.
        }
        @Override
        public String toString() {
            return this.last.get(0).getMessage();
        }
    }
}

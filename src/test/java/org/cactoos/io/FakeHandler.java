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

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.cactoos.text.JoinedText;
import org.cactoos.text.UncheckedText;

/**
 * Fake handler logger.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
public final class FakeHandler extends Handler {
    /**
     * Lines.
     */
    private final List<String> entries;
    /**
     * Ctor.
     */
    FakeHandler() {
        super();
        this.entries = new LinkedList<>();
    }
    @Override
    public void publish(final LogRecord record) {
        this.entries.add(record.getMessage());
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
        return new UncheckedText(
            new JoinedText(
                System.lineSeparator(),
                this.entries
            )
        ).asString();
    }
}

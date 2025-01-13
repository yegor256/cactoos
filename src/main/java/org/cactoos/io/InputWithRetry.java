/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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

import java.io.InputStream;
import java.time.Duration;
import org.cactoos.Input;
import org.cactoos.func.Retry;

/**
 * Input with retry.
 *
 * @since 1.0
 */
public final class InputWithRetry implements Input {

    /**
     * Retry mechanism for {@link Input} operations, attempting to open
     * an {@link InputStream} multiple times in case of failure.
     *
     * <p>The retry logic wraps around the {@link Input#stream()} method,
     * enabling conditional reattempts according to the provided retry
     * strategy. The number of attempts and the duration between attempts
     * are configurable through the {@link Retry} instance.
     */
    private final Retry<Input, InputStream> retry;

    /**
     * The original {@link Input} instance to be used for retry operations.
     *
     * <p>This input provides the underlying data stream that the retry
     * mechanism will attempt to read from. It is passed into the
     * {@link Retry} logic to enable multiple attempts at accessing the
     * input stream in case of failure.
     */
    private final Input input;

    public InputWithRetry(final Input main, final int att) {
        this(main, new Retry<>(Input::stream, att));
    }

    private InputWithRetry(final Input input, final Retry<Input, InputStream> retry) {
        this.input = input;
        this.retry = retry;
    }

    public InputWithRetry(final Input main, final int att, final Duration dur) {
        this(main, new Retry<>(Input::stream, att, dur));
    }

    @Override
    public InputStream stream() throws Exception {
        return this.retry.apply(this.input);
    }
}

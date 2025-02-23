/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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

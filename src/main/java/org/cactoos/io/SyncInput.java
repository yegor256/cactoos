/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.Input;

/**
 * Thread-safe {@link Input}.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @since 0.18
 */
public final class SyncInput implements Input {

    /**
     * The input.
     */
    private final Input origin;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param input The input
     */
    public SyncInput(final Input input) {
        this(input, input);
    }

    /**
     * Ctor.
     * @param input The input
     * @param lck The lock object
     */
    public SyncInput(final Input input, final Object lck) {
        this.origin = input;
        this.lock = lck;
    }

    @Override
    public InputStream stream() throws Exception {
        synchronized (this.lock) {
            return this.origin.stream();
        }
    }
}

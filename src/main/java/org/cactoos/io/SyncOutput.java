/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.cactoos.Output;

/**
 * Thread-safe {@link Output}.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @since 0.18
 */
public final class SyncOutput implements Output {

    /**
     * The output.
     */
    private final Output origin;

    /**
     * Sync lock.
     */
    private final Object lock;

    /**
     * Ctor.
     * @param output The output
     */
    public SyncOutput(final Output output) {
        this(output, output);
    }

    /**
     * Ctor.
     * @param output The output
     * @param lck The lock object
     */
    public SyncOutput(final Output output, final Object lck) {
        this.origin = output;
        this.lock = lck;
    }

    @Override
    public OutputStream stream() throws Exception {
        synchronized (this.lock) {
            return this.origin.stream();
        }
    }
}

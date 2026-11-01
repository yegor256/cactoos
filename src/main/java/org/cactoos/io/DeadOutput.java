/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.cactoos.Output;

/**
 * Output that accepts anything.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.1
 */
public final class DeadOutput implements Output {

    /**
     * Ctor.
     */
    public DeadOutput() {
        // nothing to init
    }

    @Override
    public OutputStream stream() {
        return new DeadOutputStream();
    }
}

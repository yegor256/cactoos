/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;

/**
 * OutputStream that accepts anything.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.1
 */
public final class DeadOutputStream extends OutputStream {

    /**
     * Ctor.
     */
    public DeadOutputStream() {
        // nothing to init
    }

    @Override
    public void write(final int data) {
        // nothing to do here
    }
}

/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.cactoos.Output;

/**
 * Output that writes to {@code stdout}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.6
 */
public final class Stdout implements Output {

    /**
     * Ctor.
     */
    public Stdout() {
        // nothing to init
    }

    @Override
    public OutputStream stream() {
        return System.out;
    }
}

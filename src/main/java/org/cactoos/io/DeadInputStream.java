/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;

/**
 * InputStream with no data.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.16
 */
public final class DeadInputStream extends InputStream {

    /**
     * Ctor.
     */
    public DeadInputStream() {
        // nothing to init
    }

    @Override
    public int read() {
        return -1;
    }
}

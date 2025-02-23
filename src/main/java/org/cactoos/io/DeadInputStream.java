/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;

/**
 * InputStream with no data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.16
 */
public final class DeadInputStream extends InputStream {

    @Override
    public int read() {
        return -1;
    }
}

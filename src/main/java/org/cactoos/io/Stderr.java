/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.cactoos.Output;

/**
 * Output that writes to {@code stderr}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.6
 */
public final class Stderr implements Output {

    @Override
    public OutputStream stream() {
        return System.err;
    }

}

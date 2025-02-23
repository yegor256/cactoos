/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.cactoos.Output;

/**
 * Output that accepts anything.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class DeadOutput implements Output {

    @Override
    public OutputStream stream() {
        return new DeadOutputStream();
    }

}

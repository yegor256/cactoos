/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.cactoos.Output;

/**
 * A decorator of {@link Output} that prevents {@link OutputStream}
 * to be closed by its performers.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class CloseShieldOutput implements Output {

    /**
     * Output to preserve.
     */
    private final Output origin;

    /**
     * Ctor.
     * @param origin Output to preserve.
     */
    public CloseShieldOutput(final Output origin) {
        this.origin = origin;
    }

    @Override
    public OutputStream stream() throws Exception {
        return new CloseShieldOutputStream(this.origin.stream());
    }
}

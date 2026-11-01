/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.Input;

/**
 * Input that reads from {@code stdin}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.6
 */
public final class Stdin implements Input {

    /**
     * Ctor.
     */
    public Stdin() {
        // nothing to init
    }

    @Override
    public InputStream stream() {
        return System.in;
    }
}

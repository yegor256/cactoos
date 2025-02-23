/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.Input;

/**
 * Input that reads from {@code stdin}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.6
 */
public final class Stdin implements Input {

    @Override
    public InputStream stream() {
        return System.in;
    }

}

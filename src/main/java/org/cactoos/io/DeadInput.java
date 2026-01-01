/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.Input;

/**
 * Input with no data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class DeadInput implements Input {

    @Override
    public InputStream stream() {
        return new DeadInputStream();
    }

}

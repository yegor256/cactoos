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
 * A decorator of {@link Input} that prevents {@link InputStream}
 * to be closed by its performers.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class CloseShieldInput implements Input {

    /**
     * Origin.
     */
    private final Input origin;

    /**
     * Ctor.
     * @param origin Origin
     */
    public CloseShieldInput(final Input origin) {
        this.origin = origin;
    }

    @Override
    public InputStream stream() throws Exception {
        return new CloseShieldInputStream(this.origin.stream());
    }

}

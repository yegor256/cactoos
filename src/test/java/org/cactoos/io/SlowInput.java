/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.cactoos.Input;

/**
 * Input that returns content in small portions.
 *
 * @since 0.12
 */
final class SlowInput implements Input {

    /**
     * Original input.
     */
    private final Input origin;

    /**
     * Ctor.
     * @param size The size of the array to encapsulate
     */
    SlowInput(final long size) {
        this((int) size);
    }

    /**
     * Ctor.
     * @param size The size of the array to encapsulate
     */
    SlowInput(final int size) {
        this(new InputOf(new ByteArrayInputStream(new byte[size])));
    }

    /**
     * Ctor.
     * @param input Original input to encapsulate and make slower
     */
    SlowInput(final Input input) {
        this.origin = input;
    }

    @Override
    public InputStream stream() throws Exception {
        return new SlowInputStream(this.origin.stream());
    }

}

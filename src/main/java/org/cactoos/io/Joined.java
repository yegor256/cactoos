/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import java.io.SequenceInputStream;
import org.cactoos.Input;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Reduced;

/**
 * Concatenation of several inputs.
 * @since 0.36
 */
public final class Joined implements Input {
    /**
     * The inputs.
     */
    private final Iterable<? extends Input> inputs;

    /**
     * Ctor.
     * @param ipts Iterable of inputs
     */
    public Joined(final Iterable<? extends Input> ipts) {
        this.inputs = ipts;
    }

    /**
     * Ctor.
     * @param first First input
     * @param rest The other inputs
     */
    public Joined(final Input first, final Input... rest) {
        this(
            new org.cactoos.iterable.Joined<>(
                first,
                new IterableOf<>(rest)
            )
        );
    }

    @Override
    public InputStream stream() throws Exception {
        return new Reduced<InputStream>(
            SequenceInputStream::new,
            new Mapped<>(
                input -> input::stream,
                this.inputs
            )
        ).value();
    }
}

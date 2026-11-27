/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.cactoos.Input;
import org.cactoos.iterable.IterableOf;

/**
 * Concatenation of several inputs.
 *
 * @since 0.36
 */
public final class Joined implements Input {

    /**
     * The inputs.
     */
    private final Iterable<? extends Input> inputs;

    /**
     * Ctor.
     *
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

    /**
     * Ctor.
     *
     * @param ipts Iterable of inputs
     */
    public Joined(final Iterable<? extends Input> ipts) {
        this.inputs = ipts;
    }

    @Override
    public InputStream stream() throws Exception {
        final List<InputStream> streams = new ArrayList<>();
        for (final Input input : this.inputs) {
            streams.add(input.stream());
        }
        final InputStream stream;
        if (streams.size() == 1) {
            stream = streams.get(0);
        } else {
            stream = Joined.joined(streams);
        }
        return stream;
    }

    private static SequenceInputStream joined(
        final List<? extends InputStream> streams
    ) {
        return new SequenceInputStream(Collections.enumeration(streams));
    }
}

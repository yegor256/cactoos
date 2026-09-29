/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.Proc;
import org.cactoos.iterable.IterableOf;

/**
 * Runs several {@link Proc}s in a row on one argument, stopping at the
 * first one that throws.
 *
 * <p>This class can be effectively used to run a chain of stages over
 * the same argument, just like {@link ForEach} runs one proc over many
 * items, but the other way round:</p>
 *
 * {@code
 * new Procs<>(pruning, planting, merging, morphing).exec(dir);
 * }
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> The type of the argument
 * @since 1.0
 */
public final class Procs<X> implements Proc<X> {

    /**
     * The origin.
     */
    private final Iterable<Proc<? super X>> origin;

    /**
     * Ctor.
     *
     * @param procs The procs to execute
     */
    @SafeVarargs
    public Procs(final Proc<? super X>... procs) {
        this(new IterableOf<>(procs));
    }

    /**
     * Ctor.
     *
     * @param procs The procs to execute
     */
    public Procs(final Iterable<Proc<? super X>> procs) {
        this.origin = procs;
    }

    @Override
    public void exec(final X input) throws Exception {
        for (final Proc<? super X> proc : this.origin) {
            proc.exec(input);
        }
    }
}

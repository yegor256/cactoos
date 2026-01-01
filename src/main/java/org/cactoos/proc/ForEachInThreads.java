/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.Proc;
import org.cactoos.func.FuncOf;
import org.cactoos.scalar.AndInThreads;

/**
 * Executes a {@link Proc} in a new Thread for each element of an
 * {@link Iterable}
 *
 * <p>
 * This class can be effectively used to iterate through a collection, just like
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)} works,
 * but with no guarantee on the output sorting:
 * </p>
 *
 * {@code
 * new ForEachInThreads(
 *    new ProcOf<>(input -> System.out.printf("\'%s\' ", input)),
 * ).execute(
 *    new IterableOf<>("Mary", "John", "William", "Napkin")
 * ); // Will print 'Mary' 'John' 'William' 'Napkin' to standard output.
 *    // Order of printing can be random.
 * }
 * <p>
 * There is no thread-safety guarantee.
 *
 * @param <X> The type to iterate over
 * @since 1.0
 */
public final class ForEachInThreads<X> implements Proc<Iterable<X>> {

    /**
     * The proc.
     */
    private final Proc<X> proc;

    /**
     * Ctor.
     *
     * @param proc The proc to execute
     */
    public ForEachInThreads(final Proc<X> proc) {
        this.proc = proc;
    }

    @Override
    public void exec(final Iterable<X> input) throws Exception {
        new AndInThreads(
            new FuncOf<>(this.proc, true), input
        ).value();
    }

}

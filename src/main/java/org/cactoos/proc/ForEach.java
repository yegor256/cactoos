/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.Proc;
import org.cactoos.func.FuncOf;
import org.cactoos.scalar.And;

/**
 * Executes a {@link Proc} for each element of an
 * {@link Iterable}
 *
 * <p>
 * This class can be effectively used to iterate through a collection, just like
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)} works:
 * </p>
 *
 * {@code
 * new ForEach(
 *    new ProcOf<>(input -> System.out.printf("\'%s\' ", input)),
 * ).execute(
 *    new IterableOf<>("Mary", "John", "William", "Napkin")
 * ); // will print 'Mary' 'John' 'William' 'Napkin' to standard output
 * }
 * <p>
 * There is no thread-safety guarantee.
 *
 * @param <X> The type to iterate over
 * @since 1.0
 */
public final class ForEach<X> implements Proc<Iterable<? extends X>> {

    /**
     * The proc.
     */
    private final Proc<? super X> proc;

    /**
     * Ctor.
     *
     * @param proc The proc to execute
     */
    public ForEach(final Proc<? super X> proc) {
        this.proc = proc;
    }

    @Override
    public void exec(final Iterable<? extends X> input) throws Exception {
        new And(
            new FuncOf<>(this.proc, true), input
        ).value();
    }

}

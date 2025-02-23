/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import org.cactoos.BiProc;
import org.cactoos.Proc;
import org.cactoos.scalar.AndWithIndex;

/**
 * Executes a {@link BiProc} for each element of an
 * {@link Iterable}
 *
 * <p>
 * This class can be effectively used to iterate through a collection similar to how
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)} works except
 * this class also passes the index of the current element to the proc:
 * </p>
 *
 * {@code
 * new ForEachWithIndex(
 *    new BiProcOf<>((input, index) -> System.out.printf("%d: \'%s\' ", index + 1, input)),
 * ).execute(
 *    new IterableOf<>("Mary", "John", "William", "Napkin")
 * ); // will print 1: 'Mary' 2: 'John' 3: 'William' 4: 'Napkin' to standard output
 * }
 * <p>
 * There is no thread-safety guarantee.
 *
 * @param <X> The type to iterate over
 * @since 1.0
 */
public final class ForEachWithIndex<X> implements Proc<Iterable<X>> {

    /**
     * The proc.
     */
    private final BiProc<X, Integer> proc;

    /**
     * Ctor.
     *
     * @param proc The proc to execute
     */
    public ForEachWithIndex(final BiProc<X, Integer> proc) {
        this.proc = proc;
    }

    @Override
    public void exec(final Iterable<X> input) throws Exception {
        new AndWithIndex(
            this.proc, input
        ).value();
    }
}

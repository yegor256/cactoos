/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.BiFunc;
import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.BiFuncOf;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Logical conjunction, with index.
 *
 * <p>This class can be effectively used to iterate through
 * a collection, just like
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)}
 * works, but with an index provided for each item:</p>
 *
 * <pre>{@code
 * new UncheckedScalar<>(
 *     new AndWithIndex(
 *         new IterableOf<>("Mary", "John", "William", "Napkin"),
 *         new BiFuncOf<>((text, index) ->
 *             System.out.printf("| idx #%d: name: %s ", index, text), true)
 *     )
 * ).value();
 * // will print "| idx #0: name: Mary | idx #1: name: John |
 * // idx #2: name: William | idx #3: name: Napkin " to console
 * }</pre>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.20
 */
public final class AndWithIndex implements Scalar<Boolean> {

    /**
     * The iterator.
     */
    private final Iterable<? extends Func<Integer, Boolean>> iterable;

    /**
     * Ctor.
     * @param proc Proc to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndWithIndex(final Proc<? super X> proc, final X... src) {
        this(new BiFuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> AndWithIndex(final BiFunc<? super X, Integer, Boolean> func,
        final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param proc Proc to use
     * @param src The iterable
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> AndWithIndex(final BiProc<? super X, Integer> proc,
        final Iterable<? extends X> src) {
        this(new BiFuncOf<>(proc, true), src);
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> AndWithIndex(final BiFunc<? super X, Integer, Boolean> func,
        final Iterable<? extends X> src) {
        this(
            new Mapped<>(
                item -> new FuncOf<>(input -> func.apply(item, input)),
                src
            )
        );
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    @SafeVarargs
    public AndWithIndex(final Func<Integer, Boolean>... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public AndWithIndex(final Iterable<? extends Func<Integer, Boolean>> src) {
        this.iterable = src;
    }

    @Override
    public Boolean value() throws Exception {
        boolean result = true;
        int pos = 0;
        for (final Func<Integer, Boolean> item : this.iterable) {
            if (!item.apply(pos)) {
                result = false;
                break;
            }
            ++pos;
        }
        return result;
    }
}

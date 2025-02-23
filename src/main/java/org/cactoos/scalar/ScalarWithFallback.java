/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import org.cactoos.Fallback;
import org.cactoos.Scalar;
import org.cactoos.func.FuncWithFallback;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterator.Filtered;
import org.cactoos.iterator.Sorted;
import org.cactoos.map.MapOf;

/**
 * Scalar with fallbacks that enable it to recover from errors.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @see FuncWithFallback
 * @since 0.31
 */
public final class ScalarWithFallback<T> implements Scalar<T> {

    /**
     * The origin scalar.
     */
    private final Scalar<? extends T> origin;

    /**
     * The fallback.
     */
    private final Iterable<? extends Fallback<? extends T>> fallbacks;

    /**
     * Ctor.
     * @param origin Original scalar
     * @param fbks The fallbacks
     */
    @SafeVarargs
    public ScalarWithFallback(
        final Scalar<? extends T> origin,
        final Fallback<? extends T>... fbks
    ) {
        this(origin, new IterableOf<>(fbks));
    }

    /**
     * Ctor.
     * @param origin Original scalar
     * @param fbks Fallbacks
     */
    public ScalarWithFallback(final Scalar<? extends T> origin,
        final Iterable<? extends Fallback<? extends T>> fbks) {
        this.origin = origin;
        this.fallbacks = fbks;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public T value() throws Exception {
        T result;
        try {
            result = this.origin.value();
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
            result = this.fallback(ex);
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Throwable ex) {
            result = this.fallback(ex);
        }
        return result;
    }

    /**
     * Finds the best fallback for the given exception type and apply it to
     * the exception or throw the original error if no fallback found.
     * @param exp The original exception
     * @return Result of the most suitable fallback
     * @throws Exception The original exception if no fallback found
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    private T fallback(final Throwable exp) throws Exception {
        final Iterator<? extends Map.Entry<Fallback<? extends T>, Integer>> candidates =
            new Sorted<>(
                Comparator.comparing(Map.Entry::getValue),
                new Filtered<>(
                    new org.cactoos.func.Flattened<>(
                        entry -> new Not(
                            new Equals<Integer, Integer>(
                                entry::getValue,
                                new Constant<>(Integer.MIN_VALUE)
                            )
                        )
                    ),
                    new MapOf<Fallback<? extends T>, Integer>(
                        fbk -> fbk,
                        fbk -> fbk.support(exp),
                        this.fallbacks
                    ).entrySet().iterator()
                )
            );
        if (candidates.hasNext()) {
            return candidates.next().getKey().apply(exp);
        } else {
            throw new Exception(
                "No fallback found - throw the original exception",
                exp
            );
        }
    }
}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.scalar;

import java.util.Comparator;
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
    private final Scalar<T> origin;

    /**
     * The fallback.
     */
    private final Iterable<Fallback<T>> fallbacks;

    /**
     * Ctor.
     * @param origin Original scalar
     * @param exception Supported exception type
     * @param fallback Function that converts the given exception into fallback value
     */
    public ScalarWithFallback(
        final Scalar<T> origin,
        final Class<? extends Throwable> exception,
        final Fallback<T> fallback
    ) {
        this(origin, new IterableOf<Class<? extends Throwable>>(exception), fallback);
    }

    /**
     * Ctor.
     * @param origin Original scalar
     * @param exceptions Supported exceptions types
     * @param fallback Function that converts the given exception into fallback value
     */
    public ScalarWithFallback(
        final Scalar<T> origin,
        final Iterable<Class<? extends Throwable>> exceptions,
        final Fallback<T> fallback
    ) {
        this(
            origin,
            new IterableOf<Fallback<T>>(
                fallback
            )
        );
    }

    /**
     * Ctor.
     * @param origin Original scalar
     * @param fbks Fallbacks
     */
    public ScalarWithFallback(final Scalar<T> origin,
        final Iterable<Fallback<T>> fbks) {
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
        final Sorted<Map.Entry<Fallback<T>, Integer>> candidates =
            new Sorted<>(
                Comparator.comparing(Map.Entry::getValue),
                new Filtered<>(
                    entry -> new Not(
                        new Equals<>(
                            entry::getValue,
                            () -> Integer.MIN_VALUE
                        )
                    ).value(),
                    new MapOf<>(
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

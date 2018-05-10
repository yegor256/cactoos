/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.iterator.Filtered;
import org.cactoos.iterator.Sorted;
import org.cactoos.map.MapOf;

/**
 * Scalar with a fallback plan.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
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
    private final Iterable<FallbackFrom<T>> fallbacks;

    /**
     * The follow up.
     */
    private final Func<T, T> follow;

    /**
     * Ctor.
     * @param origin Original scalar
     * @param fbks Fallbacks
     * @param follow Follow up function
     */
    public ScalarWithFallback(final Scalar<T> origin,
        final Iterable<FallbackFrom<T>> fbks,
        final Func<T, T> follow) {
        this.origin = origin;
        this.fallbacks = fbks;
        this.follow = follow;
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
        return this.follow.apply(result);
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
        final Sorted<Map.Entry<FallbackFrom<T>, Integer>> candidates =
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
                        fbk -> fbk.support(exp.getClass()),
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

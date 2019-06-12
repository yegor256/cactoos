/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
package org.cactoos.func;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.FallbackFrom;
import org.cactoos.scalar.InheritanceLevel;
import org.cactoos.scalar.ScalarWithFallback;

/**
 * Func with fallbacks that enable it to recover from errors.
 *
 * <p>You may register several fallbacks, each for any type of exception
 * whatsoever. If the decorated {@link Func} throws an exception that has
 * an IS-A relationship with a registered fallback's exception, then that
 * fallback's alternative {@link Func} will be used to provide a result.
 *
 * <p><strong>Example scenario:</strong> you need to fetch product data
 * from a database which may potentially not be available (SQLException).
 * As a fallback, you then fetch the data from a local cache that is
 * guaranteed not to fail. This is a sketch of what this code may look like:
 *
 * <pre>
 * {@code
 *    final Product product = new FuncWithFallback<>(
 *        id -> new SqlProduct().apply(id),
 *        new FallbackFrom<>(
 *            SQLException.class,
 *            id -> new CachedProduct().apply(id)
 *        )
 *    ).apply(id);
 * }
 * </pre>
 *
 * <p>If you register several fallback plans for exception types belonging to
 * the same hierarchy, then the fallback plan whose exception type has the
 * closest {@link InheritanceLevel} to the exception thrown will be used.
 *
 * <p><strong>Example scenario:</strong> sometimes {@code SqlProduct} from
 * above will throw {@link SQLRecoverableException} (a sub class of
 * {@link SQLException}). In such cases you may want to simply retry the same
 * {@link Func}:
 *
 * <pre>
 * {@code
 *    final Product product = new FuncWithFallback<>(
 *        id -> new SqlProduct().apply(id),
 *        new IterableOf<>(
 *            new FallbackFrom<>(
 *                SQLException.class,
 *                id -> new CachedProduct().apply(id)
 *            ),
 *            new FallbackFrom<>(
 *                SQLRecoverableException.class,
 *                id -> new SqlProduct().apply(id)    // run it again
 *            )
 *        )
 *    ).apply(id);
 * }
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @see ScalarWithFallback
 * @since 0.2
 */
public final class FuncWithFallback<X, Y> implements Func<X, Y> {

    /**
     * The func.
     */
    private final Func<X, Y> func;

    /**
     * The fallbacks.
     */
    private final Iterable<FallbackFrom<Y>> fallbacks;

    /**
     * Ctor.
     * @param fnc The func
     * @param fbk The fallback
     */
    @SuppressWarnings("unchecked")
    public FuncWithFallback(final Func<X, Y> fnc, final FallbackFrom<Y> fbk) {
        this(fnc, new IterableOf<>(fbk));
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbks The fallbacks
     */
    public FuncWithFallback(
        final Func<X, Y> fnc, final Iterable<FallbackFrom<Y>> fbks
    ) {
        this.func = fnc;
        this.fallbacks = fbks;
    }

    @Override
    public Y apply(final X input) throws Exception {
        return new ScalarWithFallback<>(
            () -> this.func.apply(input),
            this.fallbacks
        ).value();
    }

}

/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import org.cactoos.Fallback;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
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
 *        new Fallback.From<>(
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
 *            new Fallback.From<>(
 *                SQLException.class,
 *                id -> new CachedProduct().apply(id)
 *            ),
 *            new Fallback.From<>(
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
    private final Func<? super X, ? extends Y> func;

    /**
     * The fallbacks.
     */
    private final Iterable<? extends Fallback<? extends Y>> fallbacks;

    /**
     * Ctor.
     * @param fnc The func
     * @param fbks The fallbacks
     */
    @SafeVarargs
    public FuncWithFallback(final Func<? super X, ? extends Y> fnc,
        final Fallback<? extends Y>... fbks) {
        this(fnc, new IterableOf<>(fbks));
    }

    /**
     * Ctor.
     * @param fnc The func
     * @param fbks The fallbacks
     */
    public FuncWithFallback(final Func<? super X, ? extends Y> fnc,
        final Iterable<? extends Fallback<? extends Y>> fbks) {
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

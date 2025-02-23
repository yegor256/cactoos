/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Logical disjunction.
 * This class performs short-circuit evaluation in which arguments are
 * executed only if the preceding argument does not suffice to determine
 * the value of the expression.
 *
 * <p>This class can be effectively used to iterate through
 * a collection, just like
 * {@link java.util.stream.Stream#forEach(java.util.function.Consumer)}
 * works:</p>
 *
 * {@code
 * new Or(
 *    new ProcOf<>(input -> System.out.printf("\'%s\' ", input) ),
 *    new IterableOf<>("Mary", "John", "William", "Napkin")
 * ).value(); // will print 'Mary' 'John' 'William' 'Napkin' to standard output
 *            // the result of this operation is always false
 * }
 *
 * <p>This class could be also used for matching multiple boolean
 * expressions:</p>
 *
 * {@code
 * new Or(
 *    new False(),
 *    new True(),
 *    new True()
 * ).value(); // the result is true
 *
 * new Or(
 *    new False(),
 *    new False(),
 *    new False()
 * ).value(); // the result is false
 * }
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * @since 0.8
 */
public final class Or implements Scalar<Boolean> {

    /**
     * The iterator.
     */
    private final Iterable<? extends Scalar<Boolean>> origin;

    /**
     * Ctor.
     * @param proc Proc to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> Or(final Proc<? super X> proc, final X... src) {
        this(new FuncOf<>(proc, false), src);
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> Or(final Func<? super X, Boolean> func, final X... src) {
        this(func, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param proc Proc to use
     * @param src The iterable
     * @param <X> Type of items in the iterable
     * @since 0.24
     */
    public <X> Or(final Proc<? super X> proc, final Iterable<? extends X> src) {
        this(new FuncOf<>(proc, false), src);
    }

    /**
     * Ctor.
     * @param func Func to map
     * @param src The iterable
     * @param <X> Type of items in the iterable
     */
    public <X> Or(final Func<? super X, Boolean> func, final Iterable<? extends X> src) {
        this(
            new Mapped<>(
                item -> new ScalarOf<>(() -> func.apply(item)),
                src
            )
        );
    }

    /**
     * Ctor.
     * @param subject The subject
     * @param conditions Funcs to map
     * @param <X> Type of items in the iterable
     */
    @SafeVarargs
    public <X> Or(final X subject, final Func<? super X, Boolean>... conditions) {
        this(
            new Mapped<>(
                item -> new ScalarOf<>(() -> item.apply(subject)),
                new IterableOf<>(conditions)
            )
        );
    }

    /**
     * Ctor.
     * @param scalar The Scalar.
     */
    @SafeVarargs
    public Or(final Scalar<Boolean>... scalar) {
        this(new IterableOf<>(scalar));
    }

    /**
     * Ctor.
     * @param iterable The iterable.
     */
    public Or(final Iterable<? extends Scalar<Boolean>> iterable) {
        this.origin = iterable;
    }

    @Override
    public Boolean value() throws Exception {
        boolean result = false;
        for (final Scalar<Boolean> item : this.origin) {
            if (item.value()) {
                result = true;
                break;
            }
        }
        return result;
    }
}

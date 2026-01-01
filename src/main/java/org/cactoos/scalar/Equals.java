/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Equals.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * @param <R> Type of object to compare
 * @param <T> Type of object to compare
 * @since 0.9
 */
public final class Equals<R, T extends Comparable<R>> implements Scalar<Boolean> {

    /**
     * The first scalar.
     */
    private final Scalar<? extends T> first;

    /**
     * The second scalar.
     */
    private final Scalar<? extends R> second;

    /**
     * Ctor.
     * @param source The first scalar to compare.
     * @param compared The second scalar to compare.
     */
    public Equals(final T source, final R compared) {
        this(new Constant<>(source), new Constant<>(compared));
    }

    /**
     * Ctor.
     * @param source The first scalar to compare.
     * @param compared The second scalar to compare.
     */
    public Equals(final Scalar<? extends T> source, final Scalar<? extends R> compared) {
        this.first = source;
        this.second = compared;
    }

    @Override
    public Boolean value() throws Exception {
        return this.first.value().compareTo(this.second.value()) == 0;
    }
}

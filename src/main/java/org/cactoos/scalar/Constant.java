/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Constant value.
 *
 * <p>This {@link Scalar} represents a constant value which never changes.</p>
 *
 * <p>Contrary to {@link Sticky} this constant is always
 * pre-computed.</p>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. Despite that this class does NOT throw a checked
 * exception as it only returns a pre-computed value.</p>
 *
 * <p>Example:</p>
 * <pre>{@code
 *     final Scalar<String> constant = new Constant<>("Value");
 *     System.out.print("Constant is always the same: ");
 *     System.out.println(constant.value() == constant.value());
 * }</pre>
 *
 * <p>This class is thread-safe.</p>
 *
 * @param <T> Type of result
 * @see Sticky
 * @since 0.30
 */
public final class Constant<T> implements Scalar<T> {

    /**
     * Pre-computed value.
     */
    private final T val;

    /**
     * Ctor.
     * @param value The pre-computed constant.
     */
    public Constant(final T value) {
        this.val = value;
    }

    @Override
    public T value() {
        return this.val;
    }
}

/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Logical negative.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * @since 0.7
 */
public final class Not implements Scalar<Boolean> {

    /**
     * The origin scalar.
     */
    private final Scalar<Boolean> origin;

    /**
     * Ctor.
     * @param scalar The scalar
     */
    public Not(final Scalar<Boolean> scalar) {
        this.origin = scalar;
    }

    @Override
    public Boolean value() throws Exception {
        return !this.origin.value();
    }
}

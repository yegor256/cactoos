/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import org.cactoos.Scalar;

/**
 * Scalar that doesn't throw {@link Exception}, but throws
 * {@link IOException} instead.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link IOException}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator.</p>
 *
 * @param <T> Type of result
 * @since 0.4
 */
public final class IoChecked<T> implements Scalar<T> {

    /**
     * Original scalar.
     */
    private final Scalar<? extends T> origin;

    /**
     * Ctor.
     * @param scalar Encapsulated scalar
     */
    public IoChecked(final Scalar<? extends T> scalar) {
        this.origin = scalar;
    }

    @Override
    public T value() throws IOException {
        return new Checked<>(
            this.origin,
            IOException::new
        ).value();
    }

}

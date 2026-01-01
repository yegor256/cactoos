/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.cactoos.Scalar;

/**
 * Scalar that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @since 0.3
 */
public final class Unchecked<T> implements Scalar<T> {

    /**
     * Original origin.
     */
    private final Scalar<? extends T> origin;

    /**
     * Ctor.
     * @param scalar Encapsulated origin
     */
    public Unchecked(final Scalar<? extends T> scalar) {
        this.origin = scalar;
    }

    @Override
    public T value() {
        try {
            return new IoChecked<>(this.origin).value();
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}

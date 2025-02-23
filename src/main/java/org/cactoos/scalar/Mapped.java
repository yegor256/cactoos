/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.io.IOException;
import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * {@link Scalar} that apply a {@link Func} to the result of another
 * {@link Scalar}.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * <p>
 * This class implements {@link Scalar}, which throws a checked
 * {@link IOException}. This may not be convenient in many cases. To make it
 * more convenient and get rid of the checked exception you can use the
 * {@link Unchecked} decorator.
 * </p>
 *
 * @param <U> Type of result
 * @since 1.0.0
 */
public final class Mapped<U> extends ScalarEnvelope<U> {
    /**
     * Ctor.
     * @param func Map function.
     * @param scalar Scalar.
     * @param <T> Type of input.
     */
    public <T> Mapped(
        final Func<? super T, ? extends U> func,
        final Scalar<? extends T> scalar
    ) {
        super(() -> func.apply(scalar.value()));
    }

}

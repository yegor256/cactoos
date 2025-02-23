/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.function.Supplier;

/**
 * ScalarOfSupplier.
 *
 * @param <T> Element type.
 * @since 0.47
 */
public final class ScalarOfSupplier<T> extends ScalarEnvelope<T> {
    /**
     * Ctor.
     *
     * @param supplier The supplier
     */
    public ScalarOfSupplier(final Supplier<? extends T> supplier) {
        super(supplier::get);
    }

}

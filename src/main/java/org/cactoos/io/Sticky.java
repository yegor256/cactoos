/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.LengthOf;

/**
 * Input that reads only once.
 *
 * <p>Pay attention that this class is not thread-safe. It is highly
 * recommended to always decorate it with {@link SyncInput}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.6
 */
public final class Sticky implements Input {

    /**
     * The cache.
     */
    private final Scalar<byte[]> cache;

    /**
     * Ctor.
     * @param input The input
     */
    public Sticky(final Input input) {
        this.cache = new org.cactoos.scalar.Sticky<>(
            () -> {
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                new LengthOf(
                    new TeeInput(input, new OutputTo(baos))
                ).value();
                return baos.toByteArray();
            }
        );
    }

    @Override
    public InputStream stream() throws Exception {
        return new ByteArrayInputStream(
            new IoChecked<>(this.cache).value()
        );
    }

}

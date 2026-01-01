/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.IOException;
import org.cactoos.Bytes;
import org.cactoos.Fallback;
import org.cactoos.scalar.IoChecked;

/**
 * Bytes that doesn't throw checked {@link Exception},
 * but only throws {@link IOException}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.52
 */
public final class IoCheckedBytes implements Bytes {

    /**
     * Scalar with fallback.
     */
    private final IoChecked<byte[]> scalar;

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     */
    public IoCheckedBytes(final Bytes bts) {
        this(bts, new Fallback.None<>());
    }

    /**
     * Ctor.
     * @param bts Encapsulated bytes
     * @param fbk Fallback
     * @since 0.5
     */
    @SuppressWarnings({"unchecked", "PMD.AvoidRethrowingException", "PMD.AvoidCatchingThrowable"})
    public IoCheckedBytes(final Bytes bts, final Fallback<byte[]> fbk) {
        this.scalar = new IoChecked<>(
            () -> {
                byte[] ret;
                try {
                    ret = bts.asBytes();
                } catch (final IOException ex) {
                    throw ex;
                // @checkstyle IllegalCatchCheck (1 line)
                } catch (final Throwable ex) {
                    ret = fbk.apply(ex);
                }
                return ret;
            }
        );
    }

    @Override
    public byte[] asBytes() throws IOException {
        return this.scalar.value();
    }

}

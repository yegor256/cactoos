/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import org.cactoos.Bytes;
import org.cactoos.Func;
import org.cactoos.scalar.Checked;

/**
 * Bytes that throws exception of specified type.
 *
 * @param <E> Exception's type.
 * @since 0.31
 */
public final class CheckedBytes<E extends Exception> implements Bytes {

    /**
     * Original bytes.
     */
    private final Bytes origin;

    /**
     * Function that wraps exception of {@link #origin} to the required type.
     */
    private final Func<? super Exception, ? extends E> func;

    /**
     * Ctor.
     * @param orig Origin bytes.
     * @param fnc Function that wraps exceptions.
     */
    public CheckedBytes(final Bytes orig, final Func<? super Exception, ? extends E> fnc) {
        this.origin = orig;
        this.func = fnc;
    }

    @Override
    public byte[] asBytes() throws E {
        return new Checked<>(
            this.origin::asBytes,
            this.func
        ).value();
    }
}

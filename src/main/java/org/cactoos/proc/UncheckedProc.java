/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.cactoos.Proc;

/**
 * Proc that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 0.2
 */
public final class UncheckedProc<X> implements Proc<X> {

    /**
     * Original proc.
     */
    private final Proc<X> proc;

    /**
     * Ctor.
     * @param prc Encapsulated func
     */
    public UncheckedProc(final Proc<X> prc) {
        this.proc = prc;
    }

    @Override
    public void exec(final X input) {
        try {
            new IoCheckedProc<>(this.proc).exec(input);
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}

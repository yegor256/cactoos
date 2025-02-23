/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import org.cactoos.Proc;

/**
 * Proc that doesn't throw checked {@link Exception}, but
 * throws {@link IOException} instead.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 0.4
 */
public final class IoCheckedProc<X> implements Proc<X> {

    /**
     * Original proc.
     */
    private final Proc<X> proc;

    /**
     * Ctor.
     * @param prc Encapsulated func
     */
    public IoCheckedProc(final Proc<X> prc) {
        this.proc = prc;
    }

    @Override
    public void exec(final X input) throws IOException {
        new CheckedProc<>(
            this.proc,
            IOException::new
        ).exec(input);
    }

}

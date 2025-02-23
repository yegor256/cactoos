/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.cactoos.Output;
import org.cactoos.scalar.Unchecked;

/**
 * Input that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.9
 */
public final class UncheckedOutput implements Output {

    /**
     * Original output.
     */
    private final Output output;

    /**
     * Ctor.
     * @param opt Output
     */
    public UncheckedOutput(final Output opt) {
        this.output = opt;
    }

    @Override
    public OutputStream stream() {
        return new Unchecked<>(this.output::stream).value();
    }

}

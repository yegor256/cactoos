/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.Input;
import org.cactoos.scalar.Unchecked;

/**
 * Input that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.9
 */
public final class UncheckedInput implements Input {

    /**
     * Original input.
     */
    private final Input input;

    /**
     * Ctor.
     * @param ipt Input
     */
    public UncheckedInput(final Input ipt) {
        this.input = ipt;
    }

    @Override
    public InputStream stream() {
        return new Unchecked<>(this.input::stream).value();
    }

}

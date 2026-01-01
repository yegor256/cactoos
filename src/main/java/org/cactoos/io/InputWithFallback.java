/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Func;
import org.cactoos.Input;
import org.cactoos.func.IoCheckedFunc;

/**
 * Input that returns an alternative input if the main one throws
 * {@link IOException}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.9
 */
public final class InputWithFallback implements Input {

    /**
     * The main one.
     */
    private final Input main;

    /**
     * The alternative one.
     */
    private final IoCheckedFunc<? super IOException, ? extends Input> alternative;

    /**
     * Ctor.
     * @param input Main input
     */
    public InputWithFallback(final Input input) {
        this(input, new DeadInput());
    }

    /**
     * Ctor.
     * @param input Main input
     * @param alt Alternative
     */
    public InputWithFallback(final Input input, final Input alt) {
        this(input, error -> alt);
    }

    /**
     * Ctor.
     * @param input Main input
     * @param alt Alternative
     */
    public InputWithFallback(final Input input,
        final Func<? super IOException, ? extends Input> alt) {
        this(input, new IoCheckedFunc<>(alt));
    }

    /**
     * Ctor.
     * @param input Main input
     * @param alt Alternative
     */
    public InputWithFallback(final Input input,
        final IoCheckedFunc<? super IOException, ? extends Input> alt) {
        this.main = input;
        this.alternative = alt;
    }

    @Override
    public InputStream stream() throws Exception {
        InputStream stream;
        try {
            stream = this.main.stream();
        } catch (final IOException ex) {
            stream = this.alternative.apply(ex).stream();
        }
        return stream;
    }

}

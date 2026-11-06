/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Func;
import org.cactoos.Input;
import org.cactoos.Text;

/**
 * Mapped text.
 *
 * <p>This class applies a {@link Func} to the original text lazily,
 * i.e., the function is executed only when {@link Text#asString()} is called.</p>
 *
 * <p>Example usage:</p>
 *
 * <pre>{@code
 * Text original = new TextOf("hello");
 * Text upper = new Mapped(str -> str.toUpperCase(), original);
 * upper.asString(); // "HELLO"
 * }</pre>
 *
 * @since 0.47
 */
public final class Mapped extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param fnc Function to apply
     * @param input Original input
     * @since 0.73.2
     */
    public Mapped(final Func<? super String, ? extends CharSequence> fnc,
        final Input input) {
        this(fnc, new TextOf(input));
    }

    /**
     * Ctor.
     *
     * <p>Creates a mapped text by applying a function to the given text.</p>
     *
     * @param fnc Function to apply
     * @param txt Original text
     */
    public Mapped(final Func<? super String, ? extends CharSequence> fnc,
        final Text txt) {
        super(new TextOf(() -> fnc.apply(txt.asString())));
    }
}

/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.Locale;
import org.cactoos.Text;

/**
 * Text converted to upper case.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * <p>Example:</p>
 *
 * <pre>{@code
 * Text text1 = new Upper(new TextOf("hello"));
 * text1.asString(); // "HELLO
 * }</pre>
 *
 * @since 0.1
 */
public final class Upper extends TextEnvelope {

    /**
     * Ctor.
     *
     * <p>Creates an upper-case text from a character sequence
     * using {@link Locale#ENGLISH}.</p>
     *
     * @param text The text
     */
    public Upper(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     * <p>Creates an upper-case text from a {@link Text}
     * using {@link Locale#ENGLISH}.</p>
     * @param text The text
     */
    public Upper(final Text text) {
        this(text, Locale.ENGLISH);
    }

    /**
     * Ctor.
     *
     * <p>Creates an upper-case text from a {@link Text}
     * using the given {@link Locale}.</p>
     *
     * @param text The text
     * @param locale Locale
     */
    public Upper(final Text text, final Locale locale) {
        super(new Mapped(str -> str.toUpperCase(locale), text));
    }
}

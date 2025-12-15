/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.Locale;
import org.cactoos.Text;

/**
 * 
 * Text in lower case.
 * 
 * <p>This class converts the original text to lower case
 * using a specified {@link Locale}. If no locale is provided,
 * {@link Locale#ENGLISH} is used by default.</p>
 * 
 * <p>There is no thread-safety guarantee.
 *
 * <p>Example:</p>
 *
 * <pre>{@code
 * Text text1 = new Lowered(new TextOf("HELLO"));
 * text1.asString(); // "hello"
 * }</pre>
 * @since 0.1
 */
public final class Lowered extends TextEnvelope {

    /**
     * Ctor.
     * <p>Creates a lower-case text from a character sequence
     * @param text The text
     */
    public Lowered(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     * <p>Creates a lower-case text from a {@link Text}
     * @param text The text
     */
    public Lowered(final Text text) {
        this(text, Locale.ENGLISH);
    }

    /**
     * Ctor.
     * <p>Creates a lower-case text from a {@link Text}
     * @param text The text
     * @param locale The locale
     */
    public Lowered(final Text text, final Locale locale) {
        super(new Mapped(str -> str.toLowerCase(locale), text));
    }
}

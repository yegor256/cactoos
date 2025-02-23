/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.Locale;
import org.cactoos.Text;

/**
 * Text in upper case.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class Upper extends TextEnvelope {

    /**
     * Ctor.
     * @param text The text
     */
    public Upper(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public Upper(final Text text) {
        this(text, Locale.ENGLISH);
    }

    /**
     * Ctor.
     * @param text The text
     * @param locale Locale
     */
    public Upper(final Text text, final Locale locale) {
        super(new Mapped(str -> str.toUpperCase(locale), text));
    }
}

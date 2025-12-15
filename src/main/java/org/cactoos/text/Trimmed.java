/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Text without control characters (char &lt;= 32) from both ends.
 *
 * <p>This class trims the original text using
 * {@link String#trim()}, removing whitespace characters
 * from both the beginning and the end of the text.</p>
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 *<p>Example:</p>
 * <pre>{@code
 * Text text = new Trimmed(new TextOf("  hello world  "));
 * text.asString(); // "hello world"
 * }</pre>
 *
 * @since 0.1
 */
public final class Trimmed extends TextEnvelope {

    /**
     * Ctor.
     *
     * Creates a trimmed text from the given
     * @param text The text
     */
    public Trimmed(final Text text) {
        super(new Mapped(String::trim, text));
    }
}

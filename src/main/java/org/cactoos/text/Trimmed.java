/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Text without control characters (char &lt;= 32) from both ends.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class Trimmed extends TextEnvelope {

    /**
     * Ctor.
     * @param text The text
     */
    public Trimmed(final Text text) {
        super(new Mapped(String::trim, text));
    }
}

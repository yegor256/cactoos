/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

/**
 * Returns a text that is the default system line separator.
 *
 * <p>There is no thread-safety guarantee.
 * @since 1.0.0
 */
public final class Newline extends TextEnvelope {
    /**
     * Ctor.
     */
    public Newline() {
        super(new FormattedText("%n"));
    }
}

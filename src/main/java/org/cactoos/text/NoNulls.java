/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Text check for no nulls.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.11
 */
public final class NoNulls extends TextEnvelope {
    /**
     * Ctor.
     * @param text The text
     */
    public NoNulls(final Text text) {
        super(
            new TextOf(
                () -> {
                    if (text == null) {
                        throw new IllegalArgumentException(
                            "NULL instead of a valid text"
                        );
                    }
                    final String string = text.asString();
                    if (string == null) {
                        throw new IllegalStateException(
                            "NULL instead of a valid result string"
                        );
                    }
                    return string;
                }
            )
        );
    }
}

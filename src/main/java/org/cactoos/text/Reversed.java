/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Reverse the Text.
 *
 * @since 0.2
 */
public final class Reversed extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param text The text
     */
    public Reversed(final Text text) {
        super(
            new Mapped(
                string -> new StringBuilder(string).reverse().toString(),
                text
            )
        );
    }
}

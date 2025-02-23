/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.scalar.ScalarOf;
import org.cactoos.scalar.Ternary;

/**
 * Text in capitalized case,
 * changed the first character to title case as per {@link Character#toTitleCase(int)},
 * no other characters are changed.
 *
 * @since 0.46
 */
public final class Capitalized extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param text The text
     */
    public Capitalized(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public Capitalized(final Text text) {
        super(
            new Flattened(
                new Ternary<>(
                    new ScalarOf<>(() -> new Sticky(text)),
                    new org.cactoos.func.Flattened<>(IsBlank::new),
                    t -> t,
                    t -> new Concatenated(
                        new TextOf(
                            Character.toChars(
                                Character.toTitleCase(
                                    t.asString().codePointAt(0)
                                )
                            )
                        ),
                        new Sub(t, 1)
                    )
                )
            )
        );
    }
}

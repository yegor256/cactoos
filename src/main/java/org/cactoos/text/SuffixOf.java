/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.scalar.ScalarOf;
import org.cactoos.scalar.Ternary;

/**
 * Returns a text that is after given boundary.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
public final class SuffixOf extends TextEnvelope {

    /**
     * Ctor.
     * @param text Text representing the text value
     * @param boundary String after which text will be split
     */
    public SuffixOf(final CharSequence text, final CharSequence boundary) {
        this(new TextOf(text), boundary);
    }

    /**
     * Ctor.
     * @param text Text representing the text value
     * @param boundary String after which text will be split
     */
    public SuffixOf(final Text text, final CharSequence boundary) {
        super(
            new Flattened(
                new Ternary<>(
                    new ScalarOf<>(() -> new Sticky(text)),
                    (Text t) -> t.asString().contains(boundary.toString()),
                    t -> new Sub(
                        t,
                        s -> s.indexOf(boundary.toString())
                            + boundary.length()
                    ),
                    t -> new TextOf("")
                )
            )
        );
    }
}

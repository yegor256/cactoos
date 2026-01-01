/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.cactoos.Text;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.Unchecked;

/**
 * Text of {@link String}
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class TextOfString implements Text {

    /**
     * Input text.
     */
    private final String input;

    /**
     * Ctor.
     *
     * @param input The String
     */
    public TextOfString(final String input) {
        this.input = input;
    }

    @Override
    public String asString() {
        return this.input;
    }

    @Override
    public String toString() {
        return this.input;
    }

    @Override
    public int hashCode() {
        return this.input.hashCode();
    }

    @Override
    @SuppressFBWarnings("EQ_UNUSUAL")
    public boolean equals(final Object obj) {
        return new Unchecked<>(
            new Or(
                () -> this == obj,
                new And(
                    () -> obj instanceof Text,
                    () -> this.input.equals(new UncheckedText((Text) obj).asString())
                )
            )
        ).value();
    }
}

/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.Unchecked;

/**
 * Text of {@link Scalar}
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class TextOfScalar implements Text {

    /**
     * Value of the envelope.
     */
    private final Scalar<? extends CharSequence> origin;

    /**
     * Ctor.
     *
     * @param scalar The scalar of CharSequence
     */
    public TextOfScalar(final Scalar<? extends CharSequence> scalar) {
        this.origin = scalar;
    }

    @Override
    public String asString() throws Exception {
        return this.origin.value().toString();
    }

    @Override
    public String toString() {
        return new UncheckedText(this).asString();
    }

    @Override
    public int hashCode() {
        return new Unchecked<>(this.origin).value().hashCode();
    }

    @Override
    @SuppressFBWarnings("EQ_UNUSUAL")
    public boolean equals(final Object obj) {
        return new Unchecked<>(
            new Or(
                () -> this == obj,
                new And(
                    () -> obj instanceof Text,
                    () -> new UncheckedText(this)
                        .asString()
                        .equals(new UncheckedText((Text) obj).asString())
                )
            )
        ).value();
    }
}

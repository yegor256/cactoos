/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.Mapped;

/**
 * {@link Text} from {@link Scalar}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.46
 */
public final class Flattened extends TextEnvelope {
    /**
     * Ctor.
     * @param sclr The text
     */
    public Flattened(final Scalar<? extends Text> sclr) {
        super(new TextOf(new Mapped<>(Text::asString, sclr)));
    }
}

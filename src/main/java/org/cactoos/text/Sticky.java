/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Cached version of a Text.
 *
 * <p>This {@link Text} decorator technically is an in-memory
 * cache.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see org.cactoos.scalar.Sticky
 * @since 0.47
 */
public final class Sticky extends TextEnvelope {
    /**
     * Ctor.
     * @param txt Text to cache
     */
    public Sticky(final Text txt) {
        super(
            new TextOfScalar(
                new org.cactoos.scalar.Sticky<>(txt::asString)
            )
        );
    }

}

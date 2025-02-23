/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Text that is thread-safe.
 *
 * <p>Objects of this class are thread safe.</p>
 *
 * @since 0.18
 */
public final class Synced extends TextEnvelope {
    /**
     * Ctor.
     * @param text The text
     */
    public Synced(final Text text) {
        this(text, text);
    }

    /**
     * Ctor.
     * @param text The text
     * @param lck The lock
     */
    public Synced(final Text text, final Object lck) {
        super(new TextOf(new org.cactoos.scalar.Synced<>(text::asString, lck)));
    }
}

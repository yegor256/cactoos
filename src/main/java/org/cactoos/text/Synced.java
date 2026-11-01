/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Input;
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
     *
     * @param input The input
     * @since 0.73.2
     */
    public Synced(final Input input) {
        this(new TextOf(input));
    }

    /**
     * Ctor.
     *
     * @param text The text
     */
    public Synced(final Text text) {
        this(text, text);
    }

    /**
     * Ctor.
     *
     * @param input The input
     * @param lck The lock
     * @since 0.73.2
     */
    public Synced(final Input input, final Object lck) {
        this(new TextOf(input), lck);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param lck The lock
     */
    public Synced(final Text text, final Object lck) {
        super(new TextOf(new org.cactoos.scalar.Synced<>(text::asString, lck)));
    }
}

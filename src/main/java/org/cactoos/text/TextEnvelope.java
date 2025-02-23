/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * {@link Text} envelope.
 *
 * @since 0.32
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
public abstract class TextEnvelope implements Text {

    /**
     * Wrapped Text.
     */
    private final Text origin;

    /**
     * Ctor.
     * @param text Text representing the text value.
     */
    public TextEnvelope(final Text text) {
        this.origin = text;
    }

    @Override
    public final String asString() throws Exception {
        return this.origin.asString();
    }

    @Override
    public final String toString() {
        return this.origin.toString();
    }

    @Override
    public final boolean equals(final Object obj) {
        return this.origin.equals(obj);
    }

    @Override
    public final int hashCode() {
        return this.origin.hashCode();
    }
}

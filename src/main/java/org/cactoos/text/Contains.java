/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Tests if this Text contains other Text.
 *
 * @since 1.0
 */
public final class Contains implements Scalar<Boolean> {

    /**
     * The origin.
     */
    private final Text origin;

    /**
     * The other.
     */
    private final Text other;

    /**
     * Ctor.
     * @param origin The origin
     * @param other The other
     */
    public Contains(final CharSequence origin, final CharSequence other) {
        this(new TextOf(origin), new TextOf(other));
    }

    /**
     * Ctor.
     * @param origin The origin
     * @param other The other
     */
    public Contains(final CharSequence origin, final Text other) {
        this(new TextOf(origin), other);
    }

    /**
     * Ctor.
     * @param origin The origin
     * @param other The other
     */
    public Contains(final Text origin, final String other) {
        this(origin, new TextOf(other));
    }

    /**
     * Ctor.
     * @param origin The origin
     * @param other The other
     */
    public Contains(final Text origin, final Text other) {
        this.origin = origin;
        this.other = other;
    }

    @Override
    public Boolean value() throws Exception {
        return this.origin.asString().contains(this.other.asString());
    }
}

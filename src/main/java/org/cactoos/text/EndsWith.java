/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Tests if this Text ends with the specified suffix.
 *
 * @since 1.0
 */
public final class EndsWith implements Scalar<Boolean> {

    /**
     * The origin.
     */
    private final Text origin;

    /**
     * The suffix.
     */
    private final Text suffix;

    /**
     * Ctor.
     * @param origin The origin
     * @param suffix The suffix
     */
    public EndsWith(final Text origin, final CharSequence suffix) {
        this(origin, new TextOf(suffix));
    }

    /**
     * Ctor.
     * @param origin The origin
     * @param suffix The suffix
     */
    public EndsWith(final Text origin, final Text suffix) {
        this.origin = origin;
        this.suffix = suffix;
    }

    @Override
    public Boolean value() throws Exception {
        return this.origin.asString().endsWith(this.suffix.asString());
    }
}

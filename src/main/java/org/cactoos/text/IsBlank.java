/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Determines if text is blank (consists of spaces) or not.
 *
 * <p>There is no thread-safety guarantee.
 * @see IsEmpty
 * @since 0.1
 */
public final class IsBlank implements Scalar<Boolean> {

    /**
     * The text.
     */
    private final Text origin;

    /**
     * Ctor.
     * @param text The text
     */
    public IsBlank(final Text text) {
        this.origin = text;
    }

    @Override
    public Boolean value() throws Exception {
        return this.origin.asString().chars()
            .allMatch(Character::isWhitespace);
    }
}

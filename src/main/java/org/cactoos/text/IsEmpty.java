/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Determines if text is empty or not.
 * @see IsBlank
 * @since 0.47
 */
public final class IsEmpty implements Scalar<Boolean> {

    /**
     * The text.
     */
    private final Text txt;

    /**
     * Ctor.
     * @param text The text
     */
    public IsEmpty(final Text text) {
        this.txt = text;
    }

    @Override
    public Boolean value() throws Exception {
        return this.txt.asString().isEmpty();
    }
}

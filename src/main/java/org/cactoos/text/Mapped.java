/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Func;
import org.cactoos.Text;

/**
 * Mapped text.
 *
 * @since 0.47
 */
public final class Mapped extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param fnc Function to apply
     * @param txt Original text
     */
    public Mapped(final Func<? super String, ? extends CharSequence> fnc,
        final Text txt) {
        super(new TextOf(() -> fnc.apply(txt.asString())));
    }

}

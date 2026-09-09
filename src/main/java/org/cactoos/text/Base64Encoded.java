/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.bytes.BytesBase64;
import org.cactoos.bytes.BytesOf;

/**
 * Encodes the origin text using the Base64 encoding scheme.
 *
 * @since 0.20.2
 */
public final class Base64Encoded extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param input The CharSequence
     */
    public Base64Encoded(final CharSequence input) {
        this(new TextOf(input));
    }

    /**
     * Ctor.
     *
     * @param origin Origin text
     */
    public Base64Encoded(final Text origin) {
        super(new TextOf(
            new BytesBase64(
                new BytesOf(origin)
            )
        ));
    }
}

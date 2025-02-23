/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.bytes.Base64Bytes;
import org.cactoos.bytes.BytesOf;

/**
 * Decodes the origin text using the Base64 encoding scheme.
 * @since 0.20.2
 */
public final class Base64Decoded extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param input The CharSequence
     */
    public Base64Decoded(final CharSequence input) {
        this(new TextOf(input));
    }

    /**
     * Ctor.
     *
     * @param origin Origin text
     */
    public Base64Decoded(final Text origin) {
        super(new TextOf(
            new Base64Bytes(
                new BytesOf(origin)
            )
        ));
    }
}

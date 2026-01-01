/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.bytes;

import java.util.Base64;
import org.cactoos.Bytes;

/**
 * Decodes all origin bytes using the Base64 encoding scheme.
 *
 * @since 0.20.2
 */
public final class Base64Bytes implements Bytes {

    /**
     * Origin bytes.
     */
    private final Bytes origin;

    /**
     * The decoder.
     */
    private final Base64.Decoder decoder;

    /**
     * Ctor uses a RFC4648 {@link Base64.Decoder}.
     *
     * @param origin Origin bytes
     */
    public Base64Bytes(final Bytes origin) {
        this(origin, Base64.getDecoder());
    }

    /**
     * Ctor.
     *
     * @param origin Origin bytes.
     * @param dec Decoder to use.
     */
    public Base64Bytes(final Bytes origin, final Base64.Decoder dec) {
        this.origin = origin;
        this.decoder = dec;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.decoder.decode(this.origin.asBytes());
    }
}

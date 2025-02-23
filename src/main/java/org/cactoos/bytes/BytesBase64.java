/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.bytes;

import java.util.Base64;
import org.cactoos.Bytes;

/**
 * Encodes all origin bytes using the Base64 encoding scheme.
 *
 * @since 0.20.2
 */
public final class BytesBase64 implements Bytes {

    /**
     * Origin bytes.
     */
    private final Bytes origin;

    /**
     * The encoder to use.
     */
    private final Base64.Encoder encoder;

    /**
     * Ctor uses a RFC4648 {@link Base64.Encoder}.
     *
     * @param origin Origin bytes.
     */
    public BytesBase64(final Bytes origin) {
        this(origin, Base64.getEncoder());
    }

    /**
     * Ctor.
     *
     * @param origin Origin bytes.
     * @param enc The encoder to use.
     */
    public BytesBase64(final Bytes origin, final Base64.Encoder enc) {
        this.origin = origin;
        this.encoder = enc;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.encoder.encode(this.origin.asBytes());
    }

}

/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.cactoos.Bytes;
import org.cactoos.Input;

/**
 * Digest Envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 * @checkstyle AbstractClassNameCheck (500 lines)
 * @checkstyle DesignForExtensionCheck (500 lines)
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class DigestEnvelope implements Bytes {

    /**
     * The input.
     */
    private final Input source;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * The algorithm.
     */
    private final String algorithm;

    /**
     * Ctor.
     * @param input The input
     * @param algrthm The algorithm
     */
    public DigestEnvelope(final Input input, final String algrthm) {
        this(input, 16 << 10, algrthm);
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Buffer size
     * @param algrthm The algorithm
     */
    public DigestEnvelope(
        final Input input,
        final int max,
        final String algrthm
    ) {
        this.source = input;
        this.size = max;
        this.algorithm = algrthm;
    }

    @Override
    public byte[] asBytes() throws Exception {
        try (InputStream stream = this.source.stream()) {
            final MessageDigest msg = MessageDigest.getInstance(this.algorithm);
            final byte[] buf = new byte[this.size];
            while (true) {
                final int len = stream.read(buf);
                if (len < 0) {
                    break;
                }
                msg.update(buf, 0, len);
            }
            return msg.digest();
        } catch (final NoSuchAlgorithmException ex) {
            throw new IOException(ex);
        }
    }
}

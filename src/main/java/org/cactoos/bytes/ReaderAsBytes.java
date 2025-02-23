/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.cactoos.Bytes;

/**
 * Reader as {@link Bytes}.
 *
 * <p>This class is for internal use only. Use {@link BytesOf} instead.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.12
 */
final class ReaderAsBytes implements Bytes {

    /**
     * The reader.
     */
    private final Reader reader;

    /**
     * The charset.
     */
    private final CharSequence charset;

    /**
     * The buffer size.
     */
    private final int size;

    /**
     * Ctor.
     *
     * @param rdr Reader
     */
    ReaderAsBytes(final Reader rdr) {
        this(rdr, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param cset Charset
     */
    ReaderAsBytes(final Reader rdr, final Charset cset) {
        this(rdr, cset, 16 << 10);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param cset Charset
     * @since 0.13.2
     */
    ReaderAsBytes(final Reader rdr, final CharSequence cset) {
        this(rdr, cset, 16 << 10);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param cset Charset
     * @param max Buffer size
     */
    ReaderAsBytes(final Reader rdr, final Charset cset, final int max) {
        this(rdr, cset.name(), max);
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param max Buffer size
     * @since 0.13.3
     */
    ReaderAsBytes(final Reader rdr, final int max) {
        this(rdr, StandardCharsets.UTF_8, max);
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     * @param cset Charset
     * @param max Buffer size
     */
    ReaderAsBytes(final Reader rdr, final CharSequence cset, final int max) {
        this.reader = rdr;
        this.charset = cset;
        this.size = max;
    }

    @Override
    public byte[] asBytes() throws Exception {
        final char[] buffer = new char[this.size];
        final StringBuilder builder = new StringBuilder(this.size);
        while (true) {
            final int done = this.reader.read(buffer, 0, buffer.length);
            if (done < 0) {
                break;
            }
            builder.append(buffer, 0, done);
        }
        this.reader.close();
        return builder.toString().getBytes(this.charset.toString());
    }

}

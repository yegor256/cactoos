/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream that returns content in small portions.
 *
 * @since 0.12
 */
public final class SlowInputStream extends InputStream {

    /**
     * Original stream.
     */
    private final InputStream origin;

    /**
     * Ctor.
     * @param size The size of the array to encapsulate
     */
    public SlowInputStream(final int size) {
        this(new ByteArrayInputStream(new byte[size]));
    }

    /**
     * Ctor.
     * @param stream Original stream to encapsulate and make slower
     */
    SlowInputStream(final InputStream stream) {
        super();
        this.origin = stream;
    }

    @Override
    public int read() throws IOException {
        final byte[] buf = new byte[1];
        final int result;
        if (this.read(buf) < 0) {
            result = -1;
        } else {
            result = Byte.toUnsignedInt(buf[0]);
        }
        return result;
    }

    @Override
    public int read(final byte[] buf, final int offset, final int len)
        throws IOException {
        final int result;
        if (len > 1) {
            result = this.origin.read(buf, offset, len - 1);
        } else {
            result = this.origin.read(buf, offset, len);
        }
        return result;
    }

    @Override
    public int read(final byte[] buf) throws IOException {
        return this.read(buf, 0, buf.length);
    }

}

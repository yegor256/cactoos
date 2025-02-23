/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.scalar;

import java.io.InputStream;
import java.util.Iterator;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Length.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class LengthOf extends ScalarEnvelope<Long> {

    /**
     * Ctor.
     * @param items The array
     */
    public LengthOf(final Iterable<?> items) {
        this(() -> {
            final Iterator<?> iterator = items.iterator();
            long size = 0L;
            while (iterator.hasNext()) {
                iterator.next();
                ++size;
            }
            return size;
        });
    }

    /**
     * Ctor.
     * Character-length of Text.
     *
     * @param text The input
     */
    public LengthOf(final Text text) {
        this(() -> (long) text.asString().length());
    }

    /**
     * Ctor.
     * @param input The input
     */
    public LengthOf(final Input input) {
        this(input, 16 << 10);
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Buffer size
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public LengthOf(final Input input, final int max) {
        this(() -> {
            if (max == 0) {
                throw new IllegalArgumentException(
                    "Cannot use a buffer limited to zero size"
                );
            }
            try (InputStream stream = input.stream()) {
                final byte[] buf = new byte[max];
                long length = 0L;
                while (true) {
                    final int len = stream.read(buf);
                    if (len > 0) {
                        length += (long) len;
                    }
                    if (len < 0) {
                        break;
                    }
                }
                return length;
            }
        });
    }

    /**
     * Ctor.
     * @param number Number.
     */
    private LengthOf(final Scalar<Long> number) {
        super(number);
    }
}

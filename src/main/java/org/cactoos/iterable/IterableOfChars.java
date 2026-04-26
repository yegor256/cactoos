/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.iterator.IteratorOfChars;

/**
 * Iterable of characters.
 * @since 1.0
 */
public final class IterableOfChars extends IterableEnvelope<Character> {

    /**
     * Ctor.
     * @param str String
     */
    public IterableOfChars(final String str) {
        this(() -> new IteratorOfChars(str));
    }

    /**
     * Ctor.
     * @param txt Text
     */
    public IterableOfChars(final Text txt) {
        this(() -> new IteratorOfChars(txt));
    }

    /**
     * Ctor.
     * @param chars Characters
     */
    public IterableOfChars(final char... chars) {
        this(() -> new IteratorOfChars(chars));
    }

    /**
     * Ctor.
     * @param iter The iterator, deferred
     */
    private IterableOfChars(final Scalar<Iterator<Character>> iter) {
        super(new IterableOf<>(iter));
    }
}

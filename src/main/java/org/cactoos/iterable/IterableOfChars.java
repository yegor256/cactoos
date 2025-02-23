/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.cactoos.Text;
import org.cactoos.iterator.IteratorOfChars;

/**
 * Iterable of characters.
 *
 * @since 1.0
 */
public final class IterableOfChars extends IterableEnvelope<Character> {

    /**
     * Ctor.
     * @param str String
     */
    public IterableOfChars(final String str) {
        this(str.toCharArray());
    }

    /**
     * Ctor.
     * @param txt Text
     */
    public IterableOfChars(final Text txt) {
        this(txt.toString());
    }

    /**
     * Ctor.
     * @param chars Characters
     */
    public IterableOfChars(final char... chars) {
        super(new IterableOf<>(() -> new IteratorOfChars(chars)));
    }
}

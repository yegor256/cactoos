/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Text;

/**
 * {@link Iterator} that returns the {@code char}s as {@link Character}s.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.32
 */
public final class IteratorOfChars implements Iterator<Character> {
    /**
     * The list of items to iterate.
     */
    private final char[] list;

    /**
     * Current position.
     */
    private final AtomicInteger position;

    /**
     * Ctor.
     * @param str String to iterate
     */
    public IteratorOfChars(final String str) {
        this(str.toCharArray());
    }

    /**
     * Ctor.
     * @param txt Text to iterate
     */
    public IteratorOfChars(final Text txt) {
        this(txt.toString());
    }

    /**
     * Ctor.
     * @param items Items to iterate
     */
    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public IteratorOfChars(final char... items) {
        this.list = items;
        this.position = new AtomicInteger(0);
    }

    @Override
    public boolean hasNext() {
        return this.position.intValue() < this.list.length;
    }

    @Override
    public Character next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "The iterator doesn't have any more items"
            );
        }
        return this.list[this.position.getAndIncrement()];
    }
}

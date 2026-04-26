/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.scalar.Unchecked;

/**
 * Text implementing Comparable.<br>
 * Below the example how you can sort words in a string:
 * <pre>{@code
 * Iterable<Text> sorted = new Sorted<>(
 *     new Mapped<>(
 *         ComparableText::new,
 *         new SplitText("The quick brown fox jumps over the lazy dog", " ")
 *     )
 * )
 * }</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.27
 */
public final class ComparableText implements Text, Comparable<ComparableText> {

    /**
     * Wrapped text.
     */
    private final Text origin;

    /**
     * Ctor.
     * @param text The text
     */
    public ComparableText(final Text text) {
        this.origin = text;
    }

    @Override
    public String asString() throws Exception {
        return this.origin.asString();
    }

    @Override
    public int compareTo(final ComparableText other) {
        return new Unchecked<>(
            () -> this.asString().compareTo(other.asString())
        ).value();
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj || this.origin.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.origin.hashCode();
    }

    @Override
    public String toString() {
        return this.origin.toString();
    }
}

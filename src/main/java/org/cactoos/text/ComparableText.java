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
public final class ComparableText extends TextEnvelope
    implements Comparable<Text> {

    /**
     * Ctor.
     * @param text The text
     */
    public ComparableText(final Text text) {
        super(text);
    }

    @Override
    public int compareTo(final Text other) {
        return new Unchecked<>(
            () -> this.asString().compareTo(other.asString())
        ).value();
    }
}

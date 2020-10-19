/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Split the Text.
 *
 * @see String#split(String)
 * @see String#split(String, int)
 * @since 0.9
 */
public final class Split extends IterableEnvelope<Text> {
    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final String text, final String rgx) {
        this(
            new UncheckedText(new TextOf(text)),
            new UncheckedText(new TextOf(rgx))
        );
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final String text, final String rgx, final int lmt) {
        this(
            new UncheckedText(new TextOf(text)),
            new UncheckedText(new TextOf(rgx)),
            lmt
        );
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final String text, final Text rgx) {
        this(new UncheckedText(text), new UncheckedText(rgx));
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final String text, final Text rgx, final int lmt) {
        this(new UncheckedText(text), new UncheckedText(rgx), lmt);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final Text text, final String rgx) {
        this(new UncheckedText(text), new UncheckedText(rgx));
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final Text text, final String rgx, final int lmt) {
        this(new UncheckedText(text), new UncheckedText(rgx), lmt);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final Text text, final Text rgx) {
        this(new UncheckedText(text), new UncheckedText(rgx));
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final Text text, final Text rgx, final int lmt) {
        this(new UncheckedText(text), new UncheckedText(rgx), lmt);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @see String#split(String)
     */
    public Split(final UncheckedText text, final UncheckedText rgx) {
        this(text, rgx, 0);
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     * @see String#split(String, int)
     */
    public Split(final UncheckedText text, final UncheckedText rgx, final int lmt) {
        super(
            new Mapped<>(
                TextOf::new,
                new IterableOf<>(
                    text.asString().split(rgx.asString(), lmt)
                )
            )
        );
    }
}

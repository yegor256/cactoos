/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.util.Iterator;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Split the Text.
 *
 * @since 0.9
 */
public final class SplitText implements Iterable<Text> {

    /**
     * The origin string.
     */
    private final UncheckedText origin;

    /**
     * The regex.
     */
    private final UncheckedText regex;

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitText(final String text, final String rgx) {
        this(
            new UncheckedText(new TextOf(text)),
            new UncheckedText(new TextOf(rgx))
        );
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     */
    public SplitText(final String text, final Text rgx) {
        this(new UncheckedText(text), new UncheckedText(rgx));
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     */
    public SplitText(final Text text, final String rgx) {
        this(new UncheckedText(text), new UncheckedText(rgx));
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     */
    public SplitText(final Text text, final Text rgx) {
        this(new UncheckedText(text), new UncheckedText(rgx));
    }

    /**
     * Ctor.
     * @param text The text
     * @param rgx The regex
     */
    public SplitText(final UncheckedText text, final UncheckedText rgx) {
        this.origin = text;
        this.regex = rgx;
    }

    @Override
    public Iterator<Text> iterator() {
        return new Mapped<String, Text>(
            TextOf::new,
            new IterableOf<>(
                this.origin.asString().split(this.regex.asString())
            )
        ).iterator();
    }
}

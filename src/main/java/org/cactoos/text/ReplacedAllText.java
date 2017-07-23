/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

import java.io.IOException;
import java.util.Collection;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.func.IoCheckedScalar;
import org.cactoos.func.UncheckedScalar;

/**
 * Replacing all elements in a text with the replacement.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class ReplacedAllText implements Text {

    /**
     * The text to be replaced.
     */
    private final Scalar<Text> origin;

    /**
     * New Replaced text for a string needle.
     *
     * @param text The text
     * @param needle The Needle
     * @param replacement The replacement
     */
    public ReplacedAllText(
        final Text text,
        final String needle,
        final String replacement
    ) {
        this(
            () -> new StringAsText(
                text.asString().replace(needle, replacement)
            )
        );
    }

    /**
     * New replaced text from a regular expression.
     *
     * @param text The text
     * @param pattern The regular expression
     * @param replacement The replacement
     */
    public ReplacedAllText(
        final Text text,
        final RegexText pattern,
        final String replacement
    ) {
        this(
            () -> new StringAsText(
                text.asString().replaceAll(
                    pattern.asString(), replacement
                )
            )
        );
    }

    /**
     * New from an array of strings.
     *
     * @param text The text
     * @param array An array of needles
     * @param replacement The replacement
     */
    public ReplacedAllText(
        final Text text,
        final String[] array,
        final String replacement
    ) {
        this(() -> new ReplacedItemsText(text, array, replacement));
    }

    /**
     * New from an iterable.
     *
     * @param text The text
     * @param iterable An iterable of needles
     * @param replacement The replacement
     */
    public ReplacedAllText(
        final Text text,
        final Iterable<String> iterable,
        final String replacement
    ) {
        this(() -> new ReplacedItemsText(text, iterable, replacement));
    }

    /**
     * New from a collection of needles.
     *
     * @param text The text
     * @param collection An collection of needles
     * @param replacement The replacement
     */
    public ReplacedAllText(
        final Text text,
        final Collection<String> collection,
        final String replacement
    ) {
        this(() -> new ReplacedItemsText(text, collection, replacement));
    }

    /**
     * Ctor.
     *
     * @param text The strategy for replacing texts.
     */
    public ReplacedAllText(final Scalar<Text> text) {
        this.origin = text;
    }

    @Override
    public String asString() throws IOException {
        return new IoCheckedScalar<Text>(
            () -> this.origin.value()
        ).value().asString();
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedScalar<Text>(
            this.origin
        ).value().compareTo(text);
    }
}


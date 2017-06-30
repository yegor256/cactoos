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
import org.cactoos.Text;

/**
 * Replacing all elements in a text with the replacement.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.11
 */
public final class ReplacedAllText implements Text {

    /**
     * The text to be replaced.
     */
    private final Text origin;

    /**
     * New optimized replaced text for a string needle.
     *
     * @param text The text
     * @param needle Needle
     * @param replace The replace one
     */
    public ReplacedAllText(
        final Text text,
        final String needle,
        final String replace
    ) {
        this(new ReplacedText(text, needle, replace));
    }

    /**
     * New from an array of strings.
     *
     * @param text The text
     * @param array An array of needles
     * @param replace The replace one
     */
    public ReplacedAllText(
        final Text text,
        final String[] array,
        final String replace
    ) {
        this(new ReplacedItemsText(text, array, replace));
    }

    /**
     * New from an iterable.
     *
     * @param text The text
     * @param iterable An iterable of needles
     * @param replace The replace one
     */
    public ReplacedAllText(
        final Text text,
        final Iterable<String> iterable,
        final String replace
    ) {
        this(new ReplacedItemsText(text, iterable, replace));
    }

    /**
     * New from a collection of needles.
     *
     * @param text The text
     * @param collection An collection of needles
     * @param replace The replace one
     */
    public ReplacedAllText(
        final Text text,
        final Collection<String> collection,
        final String replace
    ) {
        this(new ReplacedItemsText(text, collection, replace));
    }

    /**
     * New replaced text from a regular expression. It also can
     * replace a word, char or string, but it is more
     * efficient the optimized constructor which
     * receives an String.
     *
     * @param text The text
     * @param pattern The regular expressión
     * @param replace The replace one
     */
    public ReplacedAllText(
        final Text text,
        final Text pattern,
        final String replace
    ) {
        this(new ReplacedRegexText(text, pattern, replace));
    }

    /**
     * Ctor.
     *
     * @param text The strategy for replacing texts.
     */
    public ReplacedAllText(final Text text) {
        this.origin = text;
    }

    @Override
    public String asString() throws IOException {
        return this.origin.asString();
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }
}


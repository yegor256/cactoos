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
import java.util.Collections;
import org.cactoos.Text;
import org.cactoos.list.ArrayAsIterable;
import org.cactoos.list.IterableAsList;

/**
 * Replacing all needles in the text.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.11
 */
public final class ReplacedItemsText implements Text {

    /**
     * The text to be replaced.
     */
    private final Text origin;

    /**
     * An array of items to be replaced.
     */
    private final Collection<String> needles;

    /**
     * The new string replacement.
     */
    private final String replacement;

    /**
     * New from an array of strings.
     *
     * @param text The text
     * @param array The array of needles
     * @param replace The replace one
     */
    public ReplacedItemsText(
        final Text text,
        final String[] array,
        final String replace
    ) {
        this(text, new ArrayAsIterable<>(array), replace);
    }

    /**
     * New from iterable.
     *
     * @param text The text
     * @param iterable An iterable of needles
     * @param replace The replace one
     */
    public ReplacedItemsText(
        final Text text,
        final Iterable<String> iterable,
        final String replace
    ) {
        this(text, new IterableAsList<>(iterable), replace);
    }

    /**
     * New from collection.
     *
     * @param text The text
     * @param collection A collection of needles
     * @param replace The replace one
     */
    public ReplacedItemsText(
        final Text text,
        final Collection<String> collection,
        final String replace
    ) {
        this.origin = text;
        this.needles = Collections.unmodifiableCollection(collection);
        this.replacement = replace;
    }

    @Override
    public String asString() throws IOException {
        String value = this.origin.asString();
        for (final String needle
            : this.needles.toArray(new String[this.needles.size()])
        ) {
            value = value.replace(needle, this.replacement);
        }
        return value;
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }
}


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

package org.cactoos.list;

import java.io.IOException;
import org.cactoos.Text;
import org.cactoos.text.StringAsText;

/**
 * Iterable as {@link Text}.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class IterableAsText implements Text {

    /**
     * Iterable.
     */
    private final Iterable<Text> iterable;

    /**
     * Pattern.
     */
    private final Text pattern;

    /**
     * Separator.
     */
    private final Text separator;

    /**
     * Ctor with default pattern and separator.
     * @param iterable Iterable
     */
    public IterableAsText(final Iterable<Text> iterable) {
        this(iterable, "[%s]", ", ");
    }

    /**
     * Ctor.
     * @param iterator Iterable
     * @param ptn Pattern
     * @param separator Separator
     */
    public IterableAsText(
        final Iterable<Text> iterator,
        final String ptn,
        final String separator
    ) {
        this(iterator, new StringAsText(ptn), new StringAsText(separator));
    }

    /**
     * Ctor.
     * @param iterable Iterable
     * @param pattern Pattern
     * @param separator Separator
     */
    public IterableAsText(
        final Iterable<Text> iterable,
        final Text pattern,
        final Text separator
    ) {
        this.iterable = iterable;
        this.pattern = pattern;
        this.separator = separator;
    }

    @Override
    public String asString() throws IOException {
        return new IteratorAsText(
            this.iterable.iterator(),
            this.pattern,
            this.separator
        ).asString();
    }
}

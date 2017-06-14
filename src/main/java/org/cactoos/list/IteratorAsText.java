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
import java.util.Iterator;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.cactoos.text.StringAsText;

/**
 * Iterator as {@link Text}.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.5
 */
public final class IteratorAsText implements Text {

    /**
     * Iterator.
     */
    private final Iterator<Text> iterator;

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
     * @param iterator Iterator
     */
    public IteratorAsText(final Iterator<Text> iterator) {
        this(iterator, "[%s]", ", ");
    }

    /**
     * Ctor.
     * @param iterator Iterator
     * @param ptn Pattern
     * @param separator Separator
     */
    public IteratorAsText(
        final Iterator<Text> iterator,
        final String ptn,
        final String separator
    ) {
        this(iterator, new StringAsText(ptn), new StringAsText(separator));
    }

    /**
     * Ctor.
     * @param iterator Iterator
     * @param ptn Pattern
     * @param separator Separator
     */
    public IteratorAsText(
        final Iterator<Text> iterator,
        final Text ptn,
        final Text separator
    ) {
        this.iterator = iterator;
        this.pattern = ptn;
        this.separator = separator;
    }

    @Override
    public String asString() throws IOException {
        final StringBuilder builder = new StringBuilder();
        if (this.iterator.hasNext()) {
            builder.append(this.iterator.next().asString());
        }
        final String sprt = this.separator.asString();
        while (this.iterator.hasNext()) {
            builder.append(sprt);
            builder.append(this.iterator.next().asString());
        }
        return new FormattedText(
            this.pattern, builder.toString()
        ).asString();
    }
}

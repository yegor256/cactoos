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
import org.cactoos.Text;
import org.cactoos.list.ArrayAsIterable;
import org.cactoos.list.TransformedIterable;

/**
 * Transform few texts to one.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.6
 */
public final class JoinedText implements Text {

    /**
     * Texts delimiter.
     */
    private final CharSequence delimiter;

    /**
     * Texts for join.
     */
    private final Iterable<Text> texts;

    /**
     * Ctor with default delimiter.
     * @param texts Texts array
     */
    public JoinedText(final Text... texts) {
        this(new ArrayAsIterable<>(texts));
    }

    /**
     * Ctor.
     * @param delimiter Texts delimiter
     * @param texts Texts array
     */
    public JoinedText(
        final CharSequence delimiter,
        final Text... texts
    ) {
        this(delimiter, new ArrayAsIterable<>(texts));
    }

    /**
     * Ctor with default delimiter.
     * @param texts Texts
     */
    public JoinedText(final Iterable<Text> texts) {
        this("", texts);
    }

    /**
     * Ctor.
     * @param delimiter Texts delimiter
     * @param texts Texts
     */
    public JoinedText(
        final CharSequence delimiter,
        final Iterable<Text> texts
    ) {
        this.texts = texts;
        this.delimiter = delimiter;
    }

    @Override
    public String asString() throws IOException {
        return String.join(
            this.delimiter,
            new TransformedIterable<>(
                this.texts,
                input -> new UncheckedText(input).asString()
            )
        );
    }
}

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

/**
 * Abbreviates a Text using ellipses.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.3
 */
public final class AbbreviatedText implements Text {

    /**
     * The truncated text.
     */
    private final Text origin;

    /**
     * Ctor.
     * @param text The Text
     */
    public AbbreviatedText(final Text text) {
        // @checkstyle MagicNumber (1 line)
        this(text, 0, 77);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param width Width of the result string
     */
    public AbbreviatedText(final Text text, final int width) {
        // @checkstyle MagicNumber (1 line)
        this(text, 0, width);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param offset Text position where to start
     * @param width Width of the result string
     */
    public AbbreviatedText(final Text text, final int offset, final int width) {
        this(new TruncatedText(text, offset, width));
    }

    /**
     * Ctor.
     * @param text The truncated Text
     */
    public AbbreviatedText(final TruncatedText text) {
        this.origin = text;
    }

    @Override
    public String asString() throws IOException {
        return new FormattedText(
            "%s...",
            this.origin.asString()
        ).asString();
    }
}

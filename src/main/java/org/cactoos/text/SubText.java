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
import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Extract a substring from a Text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.9
 */
public final class SubText implements Text {

    /**
     * The text.
     */
    private final Text origin;

    /**
     * The start position in the text.
     */
    private final Scalar<Integer> start;

    /**
     * The end position in the text.
     */
    private final Scalar<Integer> end;

    /**
     * Ctor.
     * @param text The String
     * @param start Start position in the text
     */
    public SubText(final String text, final int start) {
        this(new StringAsText(text), start);
    }

    /**
     * Ctor.
     * @param text The String
     * @param start Start position in the text
     * @param end End position in the text
     */
    public SubText(final String text, final int start, final int end) {
        this(new StringAsText(text), start, end);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param start Start position in the text
     */
    public SubText(final Text text, final int start) {
        this(text, () -> start, () -> text.asString().length());
    }

    /**
     * Ctor.
     * @param text The Text
     * @param start Start position in the text
     * @param end End position in the text
     */
    public SubText(final Text text, final int start, final int end) {
        this(text, () -> start, () -> end);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param start Start position in the text
     * @param end End position in the text
     */
    public SubText(final Text text, final Scalar<Integer> start,
        final Scalar<Integer> end) {
        this.origin = text;
        this.start = start;
        this.end = end;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public String asString() throws IOException {
        try {
            return this.origin.asString().substring(
                this.start.asValue(),
                this.end.asValue()
            );
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new IOException(ex);
        }
    }
}

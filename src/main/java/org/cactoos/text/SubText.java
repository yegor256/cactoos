/**
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

import java.io.IOException;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Extract a substring from a Text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.11
 */
public final class SubText implements Text {

    /**
     * The text.
     */
    private final Text origin;

    /**
     * The start position in the text.
     */
    private final UncheckedScalar<Integer> start;

    /**
     * The end position in the text.
     */
    private final UncheckedScalar<Integer> end;

    /**
     * Ctor.
     * @param text The String
     * @param strt Start position in the text
     */
    public SubText(final String text, final int strt) {
        this(new TextOf(text), strt);
    }

    /**
     * Ctor.
     * @param text The String
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public SubText(final String text, final int strt, final int finish) {
        this(new TextOf(text), strt, finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     */
    public SubText(final Text text, final int strt) {
        this(text, () -> strt, () -> text.asString().length());
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public SubText(final Text text, final int strt, final int finish) {
        this(text, () -> strt, () -> finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public SubText(final Text text, final Scalar<Integer> strt,
        final Scalar<Integer> finish) {
        this(text, new UncheckedScalar<>(strt), new UncheckedScalar<>(finish));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public SubText(final Text text, final UncheckedScalar<Integer> strt,
        final UncheckedScalar<Integer> finish) {
        this.origin = text;
        this.start = strt;
        this.end = finish;
    }

    @Override
    public String asString() throws IOException {
        int begin = this.start.value();
        if (begin < 0) {
            begin = 0;
        }
        int finish = this.end.value();
        final String text = this.origin.asString();
        if (text.length() < finish) {
            finish = text.length();
        }
        return text.substring(begin, finish);
    }

}

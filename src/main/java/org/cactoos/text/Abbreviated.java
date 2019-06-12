/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Abbreviates a Text using ellipses.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
public final class Abbreviated extends TextEnvelope {

    /**
     * The default max line width.
     */
    private static final int MAX_WIDTH = 80;

    /**
     * The ellipses width.
     */
    private static final int ELLIPSES_WIDTH = 3;

    /**
     * Ctor.
     *
     * <p> By default, the max line width is 80 characters.
     *
     * @param text The Text
     */
    public Abbreviated(final String text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     *
     * <p> By default, the max line width is 80 characters.
     *
     * @param text The Text
     */
    public Abbreviated(final Text text) {
        this(text, Abbreviated.MAX_WIDTH);
    }

    /**
     * Ctor.
     *
     * @param text A String
     * @param max Max width of the result string
     */
    public Abbreviated(final String text, final int max) {
        this(new TextOf(text), max);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param max Max width of the result string
     */
    @SuppressWarnings({
        "PMD.CallSuperInConstructor",
        "PMD.ConstructorOnlyInitializesOrCallOtherConstructors"
        })
    public Abbreviated(final Text text, final int max) {
        super((Scalar<String>) () -> {
            final Text abbreviated;
            if (text.asString().length() <= max) {
                abbreviated = text;
            } else {
                abbreviated = new FormattedText(
                    "%s...",
                    new Sub(
                        text,
                        0,
                        max - Abbreviated.ELLIPSES_WIDTH
                    ).asString()
                );
            }
            return abbreviated.asString();
        });
    }
}

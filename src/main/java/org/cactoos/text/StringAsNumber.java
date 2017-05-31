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

/**
 * String as {@link Number}.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.2
 */
public final class StringAsNumber extends Number {

    private static final long serialVersionUID = 27917942951259451L;

    /**
     * Source string.
     */
    private final String string;

    /**
     * Ctor.
     *
     * @param string Source string
     */
    public StringAsNumber(final String string) {
        super();
        this.string = string;
    }

    @Override
    public int intValue() {
        return Integer.valueOf(this.string);
    }

    @Override
    public long longValue() {
        return Long.valueOf(this.string);
    }

    @Override
    public float floatValue() {
        return Float.valueOf(this.string);
    }

    @Override
    public double doubleValue() {
        return Double.valueOf(this.string);
    }
}

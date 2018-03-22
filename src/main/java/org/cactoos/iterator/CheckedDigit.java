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

package org.cactoos.iterator;

import java.io.IOException;
import java.util.Iterator;
import org.cactoos.text.FormattedText;

/**
 * Wrap around {@link Character#digit(char, int)}.
 *
 * @author Alexander Menshikov (sharplermc@gmail.com)
 * @version $Id$
 * @since 0.30
 */
public final class CheckedDigit implements Iterator<Integer> {

    /**
     * Radix.
     */
    private final int radix;

    /**
     * Origin Character iterator.
     */
    private final Iterator<Character> origin;

    /**
     * Ctor.
     *
     * @param radix Radix.
     * @param source Source string.
     */
    public CheckedDigit(final int radix, final String source) {
        this(radix, source.toCharArray());
    }

    /**
     * Ctor.
     *
     * @param radix Radix.
     * @param source Source char array.
     */
    public CheckedDigit(final int radix, final char... source) {
        this(radix, new IteratorChar(source));
    }

    /**
     * Ctor.
     *
     * @param radix Radix.
     * @param origin Source char array.
     */
    public CheckedDigit(final int radix, final Iterator<Character> origin) {
        this.radix = radix;
        this.origin = origin;
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    public Integer next() {
        return Character.digit(this.origin.next(), this.radix);
    }

    /**
     * Checked version of next.
     *
     * @return Next digit.
     * @throws IOException If next char is unexpected.
     */
    public Integer checkedNext() throws IOException {
        final char character = this.origin.next();
        final int result = Character.digit(character, this.radix);
        if (result == -1) {
            throw new IOException(
                new FormattedText(
                    "Unexpected character '%c'",
                    character
                ).asString()
            );
        }
        return result;
    }
}

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
 * Text padded at start to reach the given length.
 *
 * @author Vivek Poddar (vivekimsit@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class PaddedTextStart implements Text {

    /**
     * The text.
     */
    private final Text origin;

    /**
     * The minimum length of the resulting string.
     */
    private final int minLength;

    /**
     * The character to be padded at the begining.
     */
    private final char padChar;

    /**
     * Ctor.
     * @param text The text
     * @param minLength The minimum length of the resulting string
     */
    public PaddedTextStart(final NotNullText text, final int minLength, final char padChar) {
        this.origin = text;
        this.padChar = padChar;
        this.minLength = minLength;
    }

    @Override
    public String asString() throws IOException {
        final String originString = this.origin.asString();
        if (originString.length() >= this.minLength) {
          return originString;
        }

        final StringBuilder sb = new StringBuilder(this.minLength);
        for (int i = originString.length(); i < this.minLength; i++) {
          sb.append(this.padChar);
        }
        sb.append(originString);
        return sb.toString();
    }
}


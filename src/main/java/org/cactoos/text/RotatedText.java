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
import org.cactoos.Text;

/**
 * Rotate (circular shift) a String of shift characters.
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class RotatedText implements Text {

    /**
     * The text.
     */
    private final Text origin;

    /**
     * The move.
     */
    private final int move;

    /**
     * Ctor.
     * @param text The text
     * @param shift The shift
     */
    public RotatedText(final Text text, final int shift) {
        this.origin = text;
        this.move = shift;
    }

    @Override
    public String asString() throws IOException {
        String text = this.origin.asString();
        final int length = text.length();
        if (length != 0 && this.move != 0 && this.move % length != 0) {
            final StringBuilder builder = new StringBuilder(length);
            int offset = -(this.move % length);
            if (offset < 0) {
                offset = text.length() + offset;
            }
            text = builder.append(
                text.substring(offset)
            ).append(
                text.substring(0, offset)
            ).toString();
        }
        return text;
    }

}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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

import org.cactoos.Text;

/**
 * Rotate (circular shift) a String of shift characters.
 * @since 0.12
 */
public final class Rotated extends TextEnvelope {

    /**
     * Ctor.
     * @param text The text
     * @param shift The shift
     */
    public Rotated(final Text text, final int shift) {
        super(
            new Mapped(
                origin -> {
                    final int length = origin.length();
                    if (length != 0 && shift != 0 && shift % length != 0) {
                        final StringBuilder builder = new StringBuilder(length);
                        int offset = -(shift % length);
                        if (offset < 0) {
                            offset = origin.length() + offset;
                        }
                        origin = builder.append(
                            origin.substring(offset)
                        ).append(
                            origin.substring(0, offset)
                        ).toString();
                    }
                    return origin;
                },
                text
            )
        );
    }
}

/*
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

import org.cactoos.Text;
import org.cactoos.bytes.Base64Bytes;
import org.cactoos.io.BytesOf;

/**
 * Decodes the origin text using the Base64 encoding scheme.
 *
 * @since 0.20.2
 */
public final class Base64Text implements Text {

    /**
     * Origin text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param input The String
     */
    public Base64Text(final String input) {
        this(new TextOf(input));
    }

    /**
     * Ctor.
     *
     * @param origin Origin text
     */
    public Base64Text(final Text origin) {
        this.origin = origin;
    }

    @Override
    public String asString() throws Exception {
        return new TextOf(
            new Base64Bytes(
                new BytesOf(this.origin)
            )
        ).asString();
    }

}

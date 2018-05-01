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
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Input;

/**
 * Input that only shows the first N bytes of the original input.
 *
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
 * @since 0.31
 */
public final class HeadInput implements Input {

    /**
     * The original input.
     */
    private final Input origin;

    /**
     * Limit of bytes that can be read from the beginning.
     */
    private final int length;

    /**
     * Ctor.
     * @param orig The original input.
     * @param len Limit of bytes that can be read from the beginning.
     */
    public HeadInput(final Input orig, final int len) {
        this.origin = orig;
        this.length = len;
    }

    @Override
    public InputStream stream() throws IOException {
        return new HeadInputStream(this.origin.stream(), this.length);
    }
}

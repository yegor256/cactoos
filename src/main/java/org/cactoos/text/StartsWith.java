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

import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Determines if text starts with a given prefix.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.44
 */
public final class StartsWith implements Scalar<Boolean> {

    /**
     * The text.
     */
    private final Text text;

    /**
     * The prefix.
     */
    private final Text prefix;

    /**
     * Ctor.
     *
     * @param text The text
     * @param prefix The prefix
     */
    public StartsWith(final Text text, final Text prefix) {
        this.text = text;
        this.prefix = prefix;
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param prefix The prefix
     */
    public StartsWith(final CharSequence text, final CharSequence prefix) {
        this(new TextOf(text), new TextOf(prefix));
    }

    @Override
    public Boolean value() throws Exception {
        return this.text.asString().startsWith(this.prefix.asString());
    }

}

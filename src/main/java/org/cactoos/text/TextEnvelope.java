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

import org.cactoos.Text;

/**
 * Text decorator for comparisons between {@link Text} implementations against
 * its {@link #asString()} value using {@link #equals(Object)} and
 * {@link #hashCode()} methods.
 * @author Paulo Lobo (pauloeduardolobo@gmail.com)
 * @version $Id$
 * @since 0.31
 */
public final class TextEnvelope implements Text {

    /**
     * Enveloped {@link Text}.
     */
    private final UncheckedText origin;

    /**
     * Ctor.
     * @param origin Text to be enveloped.
     */
    public TextEnvelope(final Text origin) {
        this.origin = new UncheckedText(origin);
    }

    @Override
    public int hashCode() {
        return this.origin.asString().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.origin.asString().equals(obj);
    }

    @Override
    public String asString() {
        return this.origin.asString();
    }
}

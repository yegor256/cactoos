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
 * Tests if this Text contains other Text.
 *
 * @since 1.0
 */
public final class Contains implements Scalar<Boolean> {

    /**
     * The origin.
     */
    private final Text origin;

    /**
     * The other.
     */
    private final Text other;

    /**
     * Ctor .
     * @param orgn The origin
     * @param othr The other
     */
    public Contains(final String orgn, final String othr) {
        this(new TextOf(orgn), new TextOf(othr));
    }

    /**
     * Ctor.
     * @param orgn The origin
     * @param othr The other
     */
    public Contains(final String orgn, final Text othr) {
        this(new TextOf(orgn), othr);
    }

    /**
     * Ctor.
     * @param orgn The origin
     * @param othr The other
     */
    public Contains(final Text orgn, final String othr) {
        this(orgn, new TextOf(othr));
    }

    /**
     * Ctor.
     * @param orgn The origin
     * @param othr The other
     */
    public Contains(final Text orgn, final Text othr) {
        this.origin = orgn;
        this.other = othr;
    }

    @Override
    public Boolean value() throws Exception {
        return this.origin.asString().contains(this.other.asString());
    }
}

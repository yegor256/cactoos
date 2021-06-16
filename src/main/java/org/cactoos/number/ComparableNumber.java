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
package org.cactoos.number;

/**
 * {@link Number} as {@link Comparable}.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class ComparableNumber extends NumberEnvelope
    implements Comparable<Number> {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -2598821437507165938L;

    /**
     * Ctor.
     *
     * @param nbr Number
     */
    public ComparableNumber(final Number nbr) {
        super(nbr);
    }

    @Override
    public int compareTo(final Number nbr) {
        return Double.compare(this.doubleValue(), nbr.doubleValue());
    }
}

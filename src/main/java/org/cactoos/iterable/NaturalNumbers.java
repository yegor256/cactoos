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
package org.cactoos.iterable;

import java.util.Iterator;
import java.util.stream.LongStream;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Iterable providing Natural Numbers.
 *
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id$
 * @since 0.7
 */
public final class NaturalNumbers implements Iterable<Long> {

    /**
     * First natural number.
     */
    private final UncheckedScalar<Long> first;

    /**
     * Last natural number.
     */
    private final UncheckedScalar<Long> last;

    /**
     * Ctor.
     */
    public NaturalNumbers() {
        this(Long.MAX_VALUE);
    }

    /**
     * Ctor.
     * @param last Last natural number
     */
    public NaturalNumbers(final long last) {
        this(0L, last);
    }

    /**
     * Ctor.
     * @param first First natural number
     * @param last Last natural number
     */
    public NaturalNumbers(final long first, final long last) {
        this(() -> first, () -> last);
    }

    /**
     * Ctor.
     * @param last Last natural number
     */
    public NaturalNumbers(final Scalar<Long> last) {
        this(new UncheckedScalar<>(last));
    }

    /**
     * Ctor.
     * @param last Last natural number
     */
    public NaturalNumbers(final UncheckedScalar<Long> last) {
        this(() -> 0L, last);
    }

    /**
     * Ctor.
     * @param first First natural number
     * @param last Last natural number
     */
    public NaturalNumbers(final Scalar<Long> first, final Scalar<Long> last) {
        this(new UncheckedScalar<>(first), new UncheckedScalar<>(last));
    }

    /**
     * Ctor.
     * @param first First natural number
     * @param last Last natural number
     */
    public NaturalNumbers(final UncheckedScalar<Long> first,
        final UncheckedScalar<Long> last) {
        this.first = first;
        this.last = last;
    }

    @Override
    public Iterator<Long> iterator() {
        return LongStream.range(
            this.first.value(),
            this.last.value()
        ).iterator();
    }
}

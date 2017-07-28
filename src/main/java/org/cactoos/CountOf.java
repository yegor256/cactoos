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
package org.cactoos;

import java.util.Iterator;
import org.cactoos.func.UncheckedScalar;
import org.cactoos.io.CountOfInput;
import org.cactoos.list.CountOfIterable;
import org.cactoos.list.CountOfIterator;
import org.cactoos.text.TextOf;

/**
 * Length of iterator.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.12
 */
public final class CountOf implements Scalar<Number> {

    /**
     * The underlying scalar number.
     */
    private final UncheckedScalar<?> scalar;

    /**
     * Ctor.
     * @param string The string
     */
    public CountOf(final String string) {
        this(new TextOf(string));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public CountOf(final Text text) {
        this(() -> text.asString().length());
    }

    /**
     * Ctor.
     * @param bytes The bytes
     */
    public CountOf(final Bytes bytes) {
        this(() -> bytes.asBytes().length);
    }

    /**
     * Ctor.
     * @param input The input
     */
    public CountOf(final Input input) {
        this(new CountOfInput(input));
    }

    /**
     * Ctor.
     * @param input The input
     * @param max Buffer size
     */
    public CountOf(final Input input, final int max) {
        this(new CountOfInput(input, max));
    }

    /**
     * Ctor.
     * @param iterable The array of items
     */
    public CountOf(final Iterable<?> iterable) {
        this(new CountOfIterable(iterable));
    }

    /**
     * Ctor.
     * @param iterator The iterator
     */
    public CountOf(final Iterator<?> iterator) {
        this(new CountOfIterator(iterator));
    }

    /**
     * Ctor.
     * @param sclr The underlying scalar length
     */
    public CountOf(final Scalar<?> sclr) {
        this.scalar = new UncheckedScalar<>(sclr);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value());
    }

    @Override
    public Number value() {
        return (Number) this.scalar.value();
    }

}


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
package org.cactoos.iterator;

import java.util.Iterator;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedScalar;

/**
 * Repeat an element.
 *
 * <p>If you need to repeat endlessly, use {@link Endless}.</p>
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.4
 */
public final class Repeated<T> implements Iterator<T> {

    /**
     * The element to repeat.
     */
    private final UncheckedScalar<T> element;

    /**
     * How many more repeats will happen.
     */
    private int left;

    /**
     * Ctor.
     * @param elm Element to repeat
     * @param max How many times to repeat
     */
    public Repeated(final T elm, final int max) {
        this(() -> elm, max);
    }

    /**
     * Ctor.
     * @param elm Element to repeat
     * @param max How many times to repeat
     */
    public Repeated(final Scalar<T> elm, final int max) {
        this(new UncheckedScalar<T>(elm), max);
    }

    /**
     * Ctor.
     * @param elm Element to repeat
     * @param max How many times to repeat
     */
    public Repeated(final UncheckedScalar<T> elm, final int max) {
        this.element = elm;
        this.left = max;
    }

    @Override
    public boolean hasNext() {
        return this.left > 0;
    }

    @Override
    public T next() {
        --this.left;
        return this.element.value();
    }
}

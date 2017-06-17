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
package org.cactoos.list;

import java.util.Iterator;
import org.cactoos.func.StickyFunc;

/**
 * A few Iterables joined together.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <T> Type of item
 * @since 0.1
 */
public final class ConcatenatedIterable<T> implements Iterable<T> {

    /**
     * Iterables.
     */
    private final Iterable<Iterable<T>> list;

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public ConcatenatedIterable(final Iterable<T>... items) {
        this(new IterableAsList<>(items));
    }

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    public ConcatenatedIterable(final Iterable<Iterable<T>> items) {
        this.list = items;
    }

    @Override
    public Iterator<T> iterator() {
        return new ConcatenatedIterator<>(
            new TransformedIterable<>(
                this.list,
                new StickyFunc<>(Iterable::iterator)
            )
        );
    }

}

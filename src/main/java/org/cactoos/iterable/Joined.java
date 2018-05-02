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
package org.cactoos.iterable;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

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
public final class Joined<T> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    @SafeVarargs
    public Joined(final Iterable<T>... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param items Items to concatenate
     * @since 0.21
     */
    public Joined(final Iterator<Iterable<T>> items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param items Items to concatenate
     */
    public Joined(final Iterable<Iterable<T>> items) {
        super(() -> {
            final Collection<Iterator<T>> iterators = new LinkedList<>();
            for (final Iterable<T> item : items) {
                iterators.add(item.iterator());
            }
            return () -> new org.cactoos.iterator.Joined<>(iterators);
        });
    }

}

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
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.list.ListOf;

/**
 * Reverse iterator.
 *
 * <p>This loads the whole wrapped Iterator in memory in order
 * to be able to reverse it.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @since 1.0
 */
public final class Reversed<X> implements Iterator<X> {

    /**
     * Origin iterator to be reversed.
     */
    private final ListIterator<? extends X> origin;

    /**
     * Ctor.
     * @param src Source iterator
     * @since 1.0
     */
    @SafeVarargs
    public Reversed(final X... src) {
        this(new IteratorOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source iterator
     * @since 1.0
     */
    public Reversed(final Iterator<? extends X> src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source list
     */
    private Reversed(final List<? extends X> src) {
        this(src.listIterator(src.size()));
    }

    /**
     * Ctor.
     * @param src Source list iterator
     */
    private Reversed(final ListIterator<? extends X> src) {
        this.origin = src;
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasPrevious();
    }

    @Override
    public X next() {
        return this.origin.previous();
    }
}

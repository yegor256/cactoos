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
package org.cactoos.collection;

import java.util.Collection;
import java.util.Iterator;
import org.cactoos.iterable.LengthOf;
import org.cactoos.list.ListOf;

/**
 * Limited collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @since 1.16
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Limited<X> implements Collection<X> {

    /**
     * Original collection.
     */
    private final Collection<X> collection;

    /**
     * Number of elements to return.
     */
    private final int limit;

    /**
     * Ctor.
     * @param src Source collection
     * @param lmt Requested number of elements
     */
    public Limited(final Iterable<X> src, final int lmt) {
        this(new ListOf<>(src), lmt);
    }

    /**
     * Ctor.
     * @param src Source collection
     * @param lmt Requested number of elements
     */
    public Limited(final Collection<X> src, final int lmt) {
        this.collection = src;
        this.limit = lmt;
    }

    @Override
    public String toString() {
        return this.collection.toString();
    }

    @Override
    public int size() {
        return new LengthOf(
            new org.cactoos.iterable.Limited<>(
                this.limit, this.collection
            )
        ).value();
    }

    @Override
    public boolean isEmpty() {
        return new LengthOf(
            new org.cactoos.iterable.Limited<>(
                this.limit, this.collection
            )
        ).value() == 0;
    }

    @Override
    public Iterator<X> iterator() {
        return new org.cactoos.iterator.Limited<>(
            this.limit, this.collection.iterator()
        );
    }

    @Override
    public boolean contains(final Object object) {
        throw new UnsupportedOperationException("#contains()");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("#toArray()");
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        throw new UnsupportedOperationException("#toArray(array)");
    }

    @Override
    public boolean add(final X item) {
        throw new UnsupportedOperationException("#add()");
    }

    @Override
    public boolean remove(final Object object) {
        throw new UnsupportedOperationException("#remove()");
    }

    @Override
    public boolean containsAll(final Collection<?> list) {
        throw new UnsupportedOperationException("#containsAll()");
    }

    @Override
    public boolean addAll(final Collection<? extends X> list) {
        throw new UnsupportedOperationException("#addAll()");
    }

    @Override
    public boolean removeAll(final Collection<?> list) {
        throw new UnsupportedOperationException("#removeAll()");
    }

    @Override
    public boolean retainAll(final Collection<?> list) {
        throw new UnsupportedOperationException("#retainAll()");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("#clear()");
    }
}

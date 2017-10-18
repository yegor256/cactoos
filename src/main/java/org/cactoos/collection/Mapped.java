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
import org.cactoos.Func;
import org.cactoos.list.ListOf;

/**
 * Mapped collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @param <Y> Type of target item
 * @since 0.14
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Mapped<X, Y> implements Collection<Y> {

    /**
     * Original collection.
     */
    private final Collection<X> collection;

    /**
     * Function.
     */
    private final Func<X, Y> func;

    /**
     * Ctor.
     * @param src Source collection
     * @param fnc Func
     */
    public Mapped(final Iterable<X> src, final Func<X, Y> fnc) {
        this(new ListOf<>(src), fnc);
    }

    /**
     * Ctor.
     * @param src Source collection
     * @param fnc Func
     */
    public Mapped(final Collection<X> src, final Func<X, Y> fnc) {
        this.collection = src;
        this.func = fnc;
    }

    @Override
    public String toString() {
        return this.collection.toString();
    }

    @Override
    public int size() {
        return this.collection.size();
    }

    @Override
    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    @Override
    public boolean contains(final Object object) {
        throw new UnsupportedOperationException("#contains()");
    }

    @Override
    public Iterator<Y> iterator() {
        return new org.cactoos.iterator.Mapped<>(
            this.func, this.collection.iterator()
        );
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
    public boolean add(final Y item) {
        throw new UnsupportedOperationException("#add()");
    }

    @Override
    public boolean remove(final Object item) {
        throw new UnsupportedOperationException("#remove()");
    }

    @Override
    public boolean containsAll(final Collection<?> list) {
        throw new UnsupportedOperationException("#containsAll()");
    }

    @Override
    public boolean addAll(final Collection<? extends Y> list) {
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

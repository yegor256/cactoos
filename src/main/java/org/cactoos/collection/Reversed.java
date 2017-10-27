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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.list.ListOf;

/**
 * Reversed collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @since 1.16
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class Reversed<X> implements Collection<X> {

    /**
     * Original collection.
     */
    private final Collection<X> collection;

    /**
     * Ctor.
     * @param src Source collection
     */
    public Reversed(final Iterable<X> src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source collection
     */
    public Reversed(final Collection<X> src) {
        this.collection = src;
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
        return this.collection.contains(object);
    }

    @Override
    public Iterator<X> iterator() {
        return new org.cactoos.iterable.Reversed<>(
            this.collection
        ).iterator();
    }

    @Override
    public Object[] toArray() {
        final List<X> list = new LinkedList<>(this.collection);
        Collections.reverse(list);
        return list.toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        final List<X> list = new LinkedList<>(this.collection);
        Collections.reverse(list);
        return list.toArray(array);
    }

    @Override
    public boolean add(final X item) {
        throw new UnsupportedOperationException("#add()");
    }

    @Override
    public boolean remove(final Object item) {
        throw new UnsupportedOperationException("#remove()");
    }

    @Override
    public boolean containsAll(final Collection<?> list) {
        return this.collection.containsAll(list);
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

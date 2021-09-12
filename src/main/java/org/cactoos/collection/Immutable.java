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
package org.cactoos.collection;

import java.util.Collection;
import java.util.Iterator;

/**
 * Decorator that doesn't allow any mutation of the wrapped {@link Collection}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Type of source item
 * @since 1.16
 * @todo #898:30min Replace all the Collections.unmodifiableCollection
 *  with the {@link org.cactoos.collection.Immutable} from the cactoos codebase.
 *  That should be done because Elegant Object principles are against static methods.
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming"
    }
)
public final class Immutable<X> implements Collection<X> {

    /**
     * Original collection.
     */
    private final Collection<? extends X> col;

    /**
     * Ctor.
     * @param src Source collection
     */
    public Immutable(final Collection<? extends X> src) {
        this.col = src;
    }

    @Override
    public int size() {
        return this.col.size();
    }

    @Override
    public boolean isEmpty() {
        return this.col.isEmpty();
    }

    @Override
    public Iterator<X> iterator() {
        return new org.cactoos.iterator.Immutable<>(this.col.iterator());
    }

    @Override
    public boolean contains(final Object object) {
        return this.col.contains(object);
    }

    @Override
    public Object[] toArray() {
        return this.col.toArray();
    }

    @Override
    @SuppressWarnings("PMD.UseVarargs")
    public <T> T[] toArray(final T[] array) {
        return this.col.toArray(array);
    }

    @Override
    public boolean add(final X item) {
        throw new UnsupportedOperationException(
            "#add(): the collection is read-only"
        );
    }

    @Override
    public boolean remove(final Object object) {
        throw new UnsupportedOperationException(
            "#remove(): the collection is read-only"
        );
    }

    @Override
    public boolean containsAll(final Collection<?> list) {
        return this.col.containsAll(list);
    }

    @Override
    public boolean addAll(final Collection<? extends X> list) {
        throw new UnsupportedOperationException(
            "#addAll(): the collection is read-only"
        );
    }

    @Override
    public boolean removeAll(final Collection<?> list) {
        throw new UnsupportedOperationException(
            "#removeAll(): the collection is read-only"
        );
    }

    @Override
    public boolean retainAll(final Collection<?> list) {
        throw new UnsupportedOperationException(
            "#retainAll(): the collection is read-only"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear(): the collection is read-only"
        );
    }

    @Override
    public String toString() {
        return this.col.toString();
    }

    @Override
    public int hashCode() {
        return this.col.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return this.col.equals(obj);
    }
}

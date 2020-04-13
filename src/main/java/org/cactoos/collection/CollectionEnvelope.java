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
import org.cactoos.iterable.IterableEnvelope;

/**
 * Base collection.
 *
 * <p>There is no thread-safety guarantee.</p>
 * @param <X> Element type
 * @since 0.23
 */
public abstract class CollectionEnvelope<X>
    extends IterableEnvelope<X> implements Collection<X> {

    /**
     * The wrapped collection.
     */
    private final Collection<X> col;

    /**
     * Ctor.
     * @param col The wrapped collection
     */
    public CollectionEnvelope(final Collection<X> col) {
        super(col);
        this.col = col;
    }

    @Override
    public final int size() {
        return this.col.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.col.isEmpty();
    }

    @Override
    public final boolean contains(final Object object) {
        return this.col.contains(object);
    }

    @Override
    public final Object[] toArray() {
        return this.col.toArray();
    }

    @Override
    public final <T> T[] toArray(final T[] array) {
        return this.col.toArray(array);
    }

    @Override
    public final boolean add(final X item) {
        return this.col.add(item);
    }

    @Override
    public final boolean remove(final Object object) {
        return this.col.remove(object);
    }

    @Override
    public final boolean containsAll(final Collection<?> list) {
        return this.col.containsAll(list);
    }

    @Override
    public final boolean addAll(final Collection<? extends X> list) {
        return this.col.addAll(list);
    }

    @Override
    public final boolean removeAll(final Collection<?> list) {
        return this.col.removeAll(list);
    }

    @Override
    public final boolean retainAll(final Collection<?> list) {
        return this.col.retainAll(list);
    }

    @Override
    public final void clear() {
        this.col.clear();
    }
}

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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Default decorator for {@link Set}.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> Set type
 * @since 0.1
 */
@SuppressWarnings("PMD.TooManyMethods")
public class SetWrap<T> implements Set<T> {

    /**
     * Origin set.
     */
    private final Set<T> origin;

    /**
     * Ctor.
     *
     * @param origin Set
     */
    protected SetWrap(final Set<T> origin) {
        this.origin = origin;
    }

    @Override
    public final int size() {
        return this.origin.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.origin.isEmpty();
    }

    @Override
    public final boolean contains(final Object item) {
        return this.origin.contains(item);
    }

    @Override
    public final Iterator<T> iterator() {
        return this.origin.iterator();
    }

    @Override
    public final Object[] toArray() {
        return this.origin.toArray();
    }

    @Override
    @SuppressWarnings({"PMD.UseVarargs", "SuspiciousToArrayCall"})
    public final <A> A[] toArray(final A[] arr) {
        return this.origin.toArray(arr);
    }

    @Override
    public final boolean add(final T item) {
        return this.origin.add(item);
    }

    @Override
    public final boolean remove(final Object item) {
        return this.origin.remove(item);
    }

    @Override
    public final boolean containsAll(final Collection<?> collection) {
        return this.origin.containsAll(collection);
    }

    @Override
    public final boolean addAll(final Collection<? extends T> collection) {
        return this.origin.addAll(collection);
    }

    @Override
    public final boolean retainAll(final Collection<?> collection) {
        return this.origin.retainAll(collection);
    }

    @Override
    public final boolean removeAll(final Collection<?> collection) {
        return this.origin.removeAll(collection);
    }

    @Override
    public final void clear() {
        this.origin.clear();
    }
}

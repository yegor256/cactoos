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
package org.cactoos.list;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.cactoos.collection.CollectionEnvelope;

/**
 * {@link List} envelope.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @since 0.23
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming"
    }
)
public abstract class ListEnvelope<T> extends CollectionEnvelope<T> implements
    List<T> {

    /**
     * Encapsulated list.
     */
    private final List<T> list;

    /**
     * Ctor.
     * @param list Encapsulated list
     */
    public ListEnvelope(final List<T> list) {
        super(list);
        this.list = list;
    }

    @Override
    public final boolean addAll(final int index,
        final Collection<? extends T> items) {
        return this.list.addAll(index, items);
    }

    @Override
    public final T get(final int index) {
        return this.list.get(index);
    }

    @Override
    public final T set(final int index, final T element) {
        return this.list.set(index, element);
    }

    @Override
    public final void add(final int index, final T element) {
        this.list.add(index, element);
    }

    @Override
    public final T remove(final int index) {
        return this.list.remove(index);
    }

    @Override
    public final int indexOf(final Object item) {
        return this.list.indexOf(item);
    }

    @Override
    public final int lastIndexOf(final Object item) {
        return this.list.lastIndexOf(item);
    }

    @Override
    public final ListIterator<T> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public final ListIterator<T> listIterator(final int index) {
        return this.list.listIterator(index);
    }

    @Override
    public final List<T> subList(final int start, final int end) {
        return this.list.subList(start, end);
    }
}

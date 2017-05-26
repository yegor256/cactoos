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

import java.util.AbstractList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * Iterable as {@link List}.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> List type
 * @since 0.1
 */
public final class IterableAsList<T> extends AbstractList<T> {

    /**
     * Origin list.
     */
    private final List<T> origin;

    /**
     * Ctor.
     *
     * @param iterable An {@link Iterable}
     */
    public IterableAsList(final Iterable<T> iterable) {
        super();
        this.origin = Collections.list(
            new IteratorAsEnumeration<>(
                iterable.iterator()
            )
        );
    }

    @Override
    public T get(final int index) {
        return this.origin.get(index);
    }

    @Override
    public int size() {
        return this.origin.size();
    }

    /**
     * Enumeration adapter.
     *
     * @param <T> Enumeration type.
     */
    private static final class IteratorAsEnumeration<T> implements
        Enumeration<T> {

        /**
         * Source.
         */
        private final Iterator<T> iterator;

        /**
         * Ctor.
         *
         * @param iterator An iterator
         */
        IteratorAsEnumeration(final Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasMoreElements() {
            return this.iterator.hasNext();
        }

        @Override
        public T nextElement() {
            return this.iterator.next();
        }
    }
}

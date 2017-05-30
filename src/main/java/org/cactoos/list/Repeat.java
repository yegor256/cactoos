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

import java.util.Iterator;

/**
 * Repeat an element.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.1
 */
public final class Repeat<T> implements Iterable<T> {

    /**
     * Element to repeat.
     */
    private final T element;

    /**
     * Repeat count.
     */
    private final int count;

    /**
     * Ctor.
     *
     * @param element To repeat
     * @param count Count
     */
    public Repeat(final T element, final int count) {
        this.element = element;
        this.count = count;
    }

    @Override
    public Iterator<T> iterator() {
        return new RepeatIterator();
    }

    /**
     * An iterator.
     */
    private final class RepeatIterator implements Iterator<T> {

        /**
         * Current position.
         */
        private int cursor;

        @Override
        public boolean hasNext() {
            return this.cursor < Repeat.this.count;
        }

        @Override
        public T next() {
            ++this.cursor;
            return Repeat.this.element;
        }
    }
}

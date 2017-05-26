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
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.cactoos.Func;

/**
 * Filtered iterator.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of item
 * @since 0.1
 */
public final class FilteredIterator<X> implements Iterator<X> {

    /**
     * Iterator.
     */
    private final Iterator<X> iterator;

    /**
     * Function.
     */
    private final Func<X, Boolean> func;

    /**
     * The buffer storing the objects of the iterator.
     */
    private final Queue<X> buffer;

    /**
     * Ctor.
     * @param src Source iterable
     * @param fnc Func
     */
    public FilteredIterator(final Iterator<X> src, final Func<X, Boolean> fnc) {
        this.iterator = src;
        this.func = fnc;
        this.buffer = new LinkedList<>();
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public boolean hasNext() {
        if (this.buffer.isEmpty()) {
            while (this.iterator.hasNext()) {
                final X object = this.iterator.next();
                try {
                    if (this.func.apply(object)) {
                        this.buffer.add(object);
                        break;
                    }
                } catch (final Exception ex) {
                    throw new IllegalStateException(ex);
                }
            }
        }
        return !this.buffer.isEmpty();
    }

    @Override
    public X next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException(
                "No more elements with fits the condition."
            );
        }
        return this.buffer.poll();
    }

    @Override
    public void remove() {
        this.iterator.remove();
    }

}

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
import java.util.LinkedList;
import org.cactoos.func.StickyScalar;
import org.cactoos.func.UncheckedScalar;

/**
 * Iterator that returns the same set of elements always.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of item
 * @since 0.8
 */
public final class StickyIterator<X> implements Iterator<X> {

    /**
     * The gate.
     */
    private final UncheckedScalar<Iterator<X>> gate;

    /**
     * Ctor.
     * @param items Items to iterate
     */
    @SafeVarargs
    public StickyIterator(final X... items) {
        this(new ArrayAsIterable<>(items).iterator());
    }

    /**
     * Ctor.
     * @param src The iterable
     */
    public StickyIterator(final Iterator<X> src) {
        this.gate = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final Collection<X> temp = new LinkedList<>();
                    while (src.hasNext()) {
                        temp.add(src.next());
                    }
                    return temp.iterator();
                }
            )
        );
    }

    @Override
    public boolean hasNext() {
        return this.gate.value().hasNext();
    }

    @Override
    public X next() {
        return this.gate.value().next();
    }
}

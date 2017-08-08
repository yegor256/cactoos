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
package org.cactoos.iterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

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
public final class Sticky<X> implements Iterator<X> {

    /**
     * The gate.
     */
    private final UncheckedScalar<Iterator<X>> gate;

    /**
     * Ctor.
     * @param items Items to iterate
     */
    @SafeVarargs
    public Sticky(final X... items) {
        this(new IterableOf<>(items).iterator());
    }

    /**
     * Ctor.
     * @param iterator The iterator
     */
    public Sticky(final Iterator<X> iterator) {
        this.gate = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final Collection<X> temp = new LinkedList<>();
                    while (iterator.hasNext()) {
                        temp.add(iterator.next());
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

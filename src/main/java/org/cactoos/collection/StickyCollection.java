/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.StickyScalar;

/**
 * Collection decorator that goes through the list only once.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <E> Type of item
 * @since 0.16
 */
public final class StickyCollection<E> extends CollectionEnvelope<E> {

    /**
     * Ctor.
     * @param items The array
     */
    @SafeVarargs
    public StickyCollection(final E... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param items The array
     * @since 0.21
     */
    public StickyCollection(final Iterator<E> items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     * @param items The array
     */
    public StickyCollection(final Iterable<E> items) {
        this(new CollectionOf<>(items));
    }

    /**
     * Ctor.
     * @param list The iterable
     */
    public StickyCollection(final Collection<E> list) {
        super(
            new StickyScalar<>(
                () -> {
                    final Collection<E> temp = new ArrayList<>(list.size());
                    temp.addAll(list);
                    return Collections.unmodifiableCollection(temp);
                }
            )
        );
    }

}

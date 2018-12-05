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
package org.cactoos.set;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.StickyScalar;

/**
 * Set decorator that goes through the set only once.
 *
 * <p>The set is read only.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of item
 * @since 0.49.2
 */
public final class StickySet<T> extends SetEnvelope<T> {

    /**
     * Ctor.
     *
     * @param items The array
     */
    @SafeVarargs
    public StickySet(final T... items) {
        this(new IterableOf<>(items));
    }

    /**
     * Ctor.
     *
     * @param items The iterable
     */
    public StickySet(final Iterable<T> items) {
        this(new SetOf<>(items));
    }

    /**
     * Ctor.
     *
     * @param items The iterator
     */
    public StickySet(final Iterator<T> items) {
        this(new SetOf<>(items));
    }

    /**
     * Ctor.
     *
     * @param items The collection
     */
    public StickySet(final Collection<T> items) {
        super(
            new StickyScalar<>(
                () -> {
                    final Set<T> tmp = new HashSet<>();
                    tmp.addAll(items);
                    return Collections.unmodifiableSet(tmp);
                }
            )
        );
    }
}

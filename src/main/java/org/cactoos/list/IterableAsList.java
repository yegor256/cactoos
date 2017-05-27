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
import java.util.ArrayList;
import java.util.List;
import org.cactoos.text.Sprintf;

/**
 * Iterable as {@link List}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> List type
 * @since 0.1
 */
public final class IterableAsList<T> extends AbstractList<T> {

    /**
     * Iterable source.
     */
    private final Iterable<T> source;

    /**
     * Cache for source.
     */
    private final List<T> cache;

    /**
     * Iterable length.
     * @todo #39:30m Needs cached `LengthOfIterable` version
     *  to improve `IterableAsList` performance. Now each call
     *  to `size()` goes through all iterable to calculate the size.
     */
    private final LengthOfIterable length;

    /**
     * Ctor.
     *
     * @param iterable An {@link Iterable}
     */
    public IterableAsList(final Iterable<T> iterable) {
        super();
        this.source = iterable;
        this.cache = new ArrayList<>(0);
        this.length = new LengthOfIterable(iterable);
    }

    @Override
    public T get(final int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(
                new Sprintf(
                    "index=%d, bounds=[%d; %d]",
                    index,
                    0,
                    this.size()
                ).asString()
            );
        }
        return this.cachedItem(index);
    }

    @Override
    public int size() {
        return this.length.asValue();
    }

    /**
     * Find item in cache by index.
     *
     * @param index Item index
     * @return Cached item
     */
    private T cachedItem(final int index) {
        if (this.cache.size() != this.size()) {
            for (final T item : this.source) {
                this.cache.add(item);
            }
        }
        return this.cache.get(index);
    }
}

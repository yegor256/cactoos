/**
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
package org.cactoos.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.cactoos.list.StickyList;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Iterator implementation for {@link Iterator} partitioning.
 *
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @param <T> Partitions value type
 * @since 0.29
 */
public class Partitioned<T> implements Iterator<List<T>> {

    /**
     * Iterator of partitions.
     */
    private final UncheckedScalar<Iterator<List<T>>> partitions;

    /**
     * Ctor.
     *
     * @param partition Size of the partitions.
     * @param src Source iterator.
     */
    public Partitioned(final int partition, final Iterator<T> src) {
        this.partitions = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    if (partition < 1) {
                        throw new IllegalArgumentException(
                            "Partition size must be > 0."
                        );
                    }
                    final List<T> base = new StickyList<>(src);
                    final List<List<T>> result = new ArrayList<>(0);
                    int start = 0;
                    while (start < base.size()) {
                        final int end =
                            start + Math.min(base.size() - start, partition);
                        result.add(base.subList(start, end));
                        start = end;
                    }
                    return result.iterator();
                }
            )
        );
    }

    @Override
    public final boolean hasNext() {
        return this.partitions.value().hasNext();
    }

    @Override
    public final List<T> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No partition left.");
        }
        return this.partitions.value().next();
    }
}

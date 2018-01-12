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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
     * Iterator to decorate.
     */
    private final Iterator<T> decorated;
    /**
     * Size of the partitions.
     */
    private final int partition;
    /**
     * Ctor.
     *
     * @param partition Size of the partitions.
     * @param src Source iterator.
     */
    public Partitioned(final int partition, final Iterator<T> src) {
        this.partition = partition;
        this.decorated = src;
    }

    @Override
    public final boolean hasNext() {
        return this.decorated.hasNext();
    }

    @Override
    public final List<T> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No partition left.");
        }
        if (this.partition < 1) {
            throw new IllegalArgumentException("Partition size < 1");
        }
        final List<T> result = new ArrayList<>(0);
        for (int count = 0; count < this.partition && this.hasNext(); ++count) {
            result.add(this.decorated.next());
        }
        return Collections.unmodifiableList(result);
    }

}

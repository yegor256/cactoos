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
import org.cactoos.Scalar;

/**
 * Length of iterator and iterable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Andriy Kryvtsun (kontiky@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class LengthOf implements Scalar<Integer> {

    /**
     * The iterator.
     */
    private final Iterator<?> iterator;

    /**
     * Ctor.
     * @param items The array
     */
    public LengthOf(final Iterable<?> items) {
        this(items.iterator());
    }

    /**
     * Ctor.
     * @param items The iterator
     */
    public LengthOf(final Iterator<?> items) {
        this.iterator = items;
    }

    @Override
    public Integer asValue() {
        int size = 0;
        while (this.iterator.hasNext()) {
            this.iterator.next();
            ++size;
        }
        return size;
    }
}

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
package org.cactoos.collection;

import java.util.Collection;
import org.cactoos.iterable.IterableOf;

/**
 * Tail portion of the collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @param <T> Element type
 * @since 0.31
 */
public final class TailOf<T> extends CollectionEnvelope<T> {

    /**
     * Ctor.
     * @param num Number of tail elements
     * @param src Source elements
     */
    @SafeVarargs
    public TailOf(final int num, final T... src) {
        this(num, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param num Number of tail elements
     * @param src Source iterable
     */
    public TailOf(final int num, final Iterable<T> src) {
        this(num, new CollectionOf<T>(src));
    }

    /**
     * Ctor.
     * @param num Number of tail elements
     * @param src Source collection
     */
    public TailOf(final int num, final Collection<T> src) {
        super(() -> new CollectionOf<T>(
            new org.cactoos.iterable.TailOf<T>(num, src)
        ));
    }
}
